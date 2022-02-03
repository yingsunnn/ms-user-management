package com.ying.msusermanagement.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

  private long DEFAULT_EXPIRE_IN_SECONDS = 60;

  private String secret = "abcd1234";
  private Algorithm algorithm = Algorithm.HMAC256(secret);

  public String generateJWTToken(String username, String role) {

    Instant expiresAt = Instant.now().plus(DEFAULT_EXPIRE_IN_SECONDS * 1000, ChronoUnit.MILLIS);

    String jwtToken = JWT.create()
        .withIssuer("Simple Solution")
        .withClaim("username", username)
        .withClaim("role", role)
        .withExpiresAt(Date.from(expiresAt))
        .sign(algorithm);

    return jwtToken;
  }

  public boolean verifyJWTToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(algorithm)
          .withIssuer("Simple Solution")
          .acceptExpiresAt(DEFAULT_EXPIRE_IN_SECONDS)
          .build();

      verifier.verify(token);
      return true;
    } catch (JWTVerificationException ex) {
      return false;
    }
  }

  public String getClaimFromToken(String token, String claimKey) {
    DecodedJWT decodedJWT = JWT.decode(token);
    return decodedJWT.getClaims().get(claimKey).toString();
  }

}
