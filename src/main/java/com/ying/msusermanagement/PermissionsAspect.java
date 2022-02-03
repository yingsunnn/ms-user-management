package com.ying.msusermanagement;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Objects;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

@Aspect
@Component
public class PermissionsAspect {

  @Around("@annotation(Permissions)")
  public Object checkPermissions(ProceedingJoinPoint joinPoint) throws Throwable {

    MethodSignature signature = (MethodSignature) joinPoint.getSignature();
    Method method = signature.getMethod();

    Integer tokenIndex = null;

    // Get token
    for (int i = 0 ; i < method.getParameters().length ; i ++) {
      Parameter parameter = method.getParameters()[i];

      if (Arrays.stream(parameter.getAnnotations())
          .anyMatch(annotation -> annotation instanceof RequestHeader && "Authorization".equals(((RequestHeader) annotation).value()))) {
        tokenIndex = i;
        continue;
      }
    }

    if (Objects.isNull(tokenIndex)) {
      throw new RuntimeException("Oauth header (Authorization) doesn't exist.");
    }

    String token = String.valueOf(joinPoint.getArgs()[tokenIndex]);
    System.out.println("token: " + token);

    // Get permissions
    Permissions permissions = method.getAnnotation(Permissions.class);

    Arrays.stream(permissions.value()).forEach(
        permission -> System.out.println("Permission: " + permission)
    );

    return joinPoint.proceed();
  }
}
