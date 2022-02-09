package com.ying.msusermanagement.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.sql.Date;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class JWTService {

  public static final String CLAIM_USER_ID = "USER_ID";
  public static final String CLAIM_CREDENTIAL_TYPE = "CREDENTIAL_TYPE";
  public static final String CLAIM_CREDENTIAL_ID = "CREDENTIAL_ID";
  public static final String CLAIM_ROLE_IDS = "ROLE_IDS";

  @Value("${jwt.expireInHour}")
  private long expireInHour = 24;

  @Value("${jwt.issuer}")
  private String issuer;

  @Value("${jwt.acceptExpiresInSecond}")
  private long acceptExpiresInSecond;

  @Value("${jwt.secret}")
  private String secret;

  private Algorithm algorithm;

  public String generateJWTToken(String userId, String credentialType, String credentialId, String roleIds) {

    Instant expiresAt = Instant.now().plus(expireInHour, ChronoUnit.HOURS);

    String jwtToken = JWT.create()
        .withIssuer(issuer)
        .withClaim(CLAIM_USER_ID, userId)
        .withClaim(CLAIM_CREDENTIAL_TYPE, credentialType)
        .withClaim(CLAIM_CREDENTIAL_ID, credentialId)
        .withClaim(CLAIM_ROLE_IDS, roleIds)
        .withExpiresAt(Date.from(expiresAt))
        .sign(this.getAlgorithm());

    return jwtToken;
  }

  public boolean verifyJWTToken(String token) {
    try {
      JWTVerifier verifier = JWT.require(this.getAlgorithm())
          .withIssuer(issuer)
          .acceptExpiresAt(acceptExpiresInSecond)
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

  private Algorithm getAlgorithm () {
    if (Objects.isNull(algorithm))
      algorithm = Algorithm.HMAC256(secret);

    return algorithm;
  }
}
