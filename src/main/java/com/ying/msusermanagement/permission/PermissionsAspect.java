package com.ying.msusermanagement.permission;

import com.ying.msusermanagement.dto.PermissionDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.exception.PermissionDeniedException;
import com.ying.msusermanagement.service.JWTService;
import com.ying.msusermanagement.service.RoleService;
import com.ying.msusermanagement.service.UserService;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
@Slf4j
@AllArgsConstructor
public class PermissionsAspect {

  private JWTService jwtService;
  private RoleService roleService;
  private UserService userService;

  @Around("@annotation(com.ying.msusermanagement.permission.Permissions)")
  public Object checkPermissions(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();

    String userAccessToken = this.getUserToken();
    String userId = this.jwtService.getClaimFromToken(userAccessToken, JWTService.CLAIM_USER_ID);
    UserDto user = this.userService.getUserForPermissionVerification(userId);

    this.verifyPermissions(joinPoint, user);

    // Set user argument
    Integer paraIndex = this.getAuthenticatedUserParaIndex(joinPoint);
    if (Objects.nonNull(paraIndex) && paraIndex >= 0) {
      args[paraIndex] = user;
    }
    return joinPoint.proceed(args);
  }

  private void verifyPermissions(ProceedingJoinPoint joinPoint, UserDto user) {
    // get method permissions
    Set<String> methodPermissions = this.getMethodPermissions(joinPoint);

    if (CollectionUtils.isEmpty(methodPermissions)) {
      return ;
    }

    // get user permissions
    Set<PermissionDto> userPermissions = this.roleService.mapRolePermissionToPermission(user.getRoles());

    // verify permissions
    if (!methodPermissions.stream().anyMatch(
        methodPermission -> userPermissions.stream().anyMatch(
            userPermission -> methodPermission.equals(userPermission.getName())))) {
      throw new PermissionDeniedException(
          "User (" + user.getId() + ") doesn't have any permissions which matches " + methodPermissions);
    }
  }

  private Integer getAuthenticatedUserParaIndex (ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    Integer tokenIndex = null;

    for (int i = 0; i < method.getParameters().length; i++) {
      Parameter parameter = method.getParameters()[i];

      if (Arrays.stream(parameter.getAnnotations()).anyMatch(
          annotation -> annotation instanceof AuthenticatedUser) &&
          parameter.getType().equals(UserDto.class)) {
        tokenIndex = i;
        continue;
      }
    }

    return tokenIndex;
  }

  private String getUserToken () {
    HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    String token = request.getHeader("Authorization");

    if (StringUtils.isBlank(token)) {
      throw new RuntimeException("Oauth header (Authorization) doesn't exist.");
    }

    return StringUtils.removeStartIgnoreCase(token, "Bearer ");
  }

  private String getUserToken(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    Integer tokenIndex = null;

    // Get token
    for (int i = 0; i < method.getParameters().length; i++) {
      Parameter parameter = method.getParameters()[i];

      if (Arrays.stream(parameter.getAnnotations())
          .anyMatch(annotation -> annotation instanceof RequestHeader
              && "Authorization".equals(((RequestHeader) annotation).value()))) {
        tokenIndex = i;
        continue;
      }
    }

    if (Objects.isNull(tokenIndex)) {
      throw new RuntimeException("Oauth header (Authorization) doesn't exist.");
    }

    String token = String.valueOf(joinPoint.getArgs()[tokenIndex]);

    return StringUtils.removeStartIgnoreCase(token, "Bearer ");
  }

  private Set<String> getMethodPermissions(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    // Get permissions
    Permissions permissions = method.getAnnotation(Permissions.class);

    return Set.of(permissions.value());
  }
}
