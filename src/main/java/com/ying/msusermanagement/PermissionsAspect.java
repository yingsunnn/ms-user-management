package com.ying.msusermanagement;

import com.ying.msusermanagement.service.JWTService;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Aspect
@Component
@AllArgsConstructor
public class PermissionsAspect {

  private JWTService jwtService;

  @Around("@annotation(Permissions)")
  public Object checkPermissions(ProceedingJoinPoint joinPoint) throws Throwable {
    Object[] args = joinPoint.getArgs();
    args[0] = "aaaa";

    this.authenticateUser(joinPoint);
    this.verifyPermissions(joinPoint);

    return joinPoint.proceed(args);
  }

  private void authenticateUser(ProceedingJoinPoint joinPoint) {
    String userToken = this.getUserToken(joinPoint);

    if (this.jwtService.verifyJWTToken(userToken)) {
      return;
    } else {
//      throw new RuntimeException("token verification failed.");
    }

  }

  private void verifyPermissions(ProceedingJoinPoint joinPoint) {
    List<String> permissions = this.getMethodPermissions(joinPoint);
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

    return token;
  }

  private List<String> getMethodPermissions(ProceedingJoinPoint joinPoint) {
    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();
    // Get permissions
    Permissions permissions = method.getAnnotation(Permissions.class);

    Arrays.stream(permissions.value()).forEach(
        permission -> System.out.println("Permission: " + permission)
    );

    return List.of(permissions.value());
  }

}
