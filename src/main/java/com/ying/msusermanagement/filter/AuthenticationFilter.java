package com.ying.msusermanagement.filter;

import com.google.common.collect.ImmutableList;
import com.ying.msusermanagement.exception.AuthenticationException;
import com.ying.msusermanagement.service.JWTService;
import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(1)
@Slf4j
@AllArgsConstructor
public class AuthenticationFilter implements Filter {

  private JWTService jwtService;

  private static List<Endpoint> excludedEndpoints = ImmutableList.of(
      // login
      Endpoint.builder()
          .method("POST")
          .uri("/users/authentication/{userId}")
          .build(),

      // create new user
      Endpoint.builder()
          .method("POST")
          .uri("/users")
          .build(),

      // User Check if user credential exist
      Endpoint.builder()
          .method("GET")
          .uri("/users/credentials")
          .build()
  );

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {
    Filter.super.init(filterConfig);
  }

  @Override
  public void doFilter(
      ServletRequest servletRequest,
      ServletResponse servletResponse,
      FilterChain filterChain
  ) throws IOException, ServletException {

    HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
    HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

    // Exclude endpoints
    Endpoint currentEndpoint = Endpoint.builder()
        .method(httpServletRequest.getMethod())
        .uri(httpServletRequest.getRequestURI())
        .build();
    if (true == excludedEndpoints.stream().anyMatch(
        endpoint -> endpoint.equals(currentEndpoint))) {
      log.debug("Endpoint " + currentEndpoint + " doesn't need to verify the access token");

      // continue
      filterChain.doFilter(servletRequest, servletResponse);
      return;
    }

    // Verify access token
    String token = Optional.ofNullable(httpServletRequest.getHeader("Authorization"))
        .map(t -> StringUtils.removeStartIgnoreCase(t, "Bearer "))
        .orElseThrow(() -> new AuthenticationException("Must provide access token.")
//        () -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "111Must provide access token.")
    );

    if (false == this.jwtService.verifyJWTToken(token)) {
      throw new AuthenticationException("Access token verification failed.");
    }

    // continue
    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    Filter.super.destroy();
  }

  @Data
  @Builder
  @Accessors(chain = true)
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Endpoint {

    private String method;
    private String uri;

    @Override
    public boolean equals(Object obj) {
      if (Objects.isNull(method) || Objects.isNull(uri)) {
        return false;
      }

      Endpoint endpointB = (Endpoint) obj;

      if (!method.equals(endpointB.getMethod())) {
        return false;
      }

      return this.uriEquals(uri, endpointB.uri);
    }

    private boolean uriEquals(
        String uriA,
        String uriB
    ) {

      if (StringUtils.isBlank(uriA) || StringUtils.isBlank(uriB)) {
        return false;
      }

      String[] uriAArr = uriA.split("/");
      String[] uriBArr = uriB.split("/");

      if (uriAArr.length != uriBArr.length) {
        return false;
      }

      for (int i = 0; i < uriAArr.length; i++) {
        if (uriAArr[i].indexOf("}") >= 0 || uriBArr[i].indexOf("}") >= 0) {
          continue;
        } else {
          if (!uriAArr[i].equals(uriBArr[i])) {
            return false;
          }
        }
      }

      return true;
    }
  }
}
