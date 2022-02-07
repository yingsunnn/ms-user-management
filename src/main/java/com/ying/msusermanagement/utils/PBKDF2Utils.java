package com.ying.msusermanagement.utils;

import com.ying.msusermanagement.exception.UserManagementException;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class PBKDF2Utils {

  public static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

  public static final int SALT_SIZE = 128 / 2;
  public static final int HASH_SIZE = 256 * 4;
  public static final int PBKDF2_ITERATIONS = 1000;

  public boolean authenticate (String attemptedPassword, String encryptedPassword, String salt) {
    String encryptedAttemptedPassword = encryptPassword(attemptedPassword, salt);
    return encryptedAttemptedPassword.equals(encryptedPassword);
  }

  public String encryptPassword (String password, String salt) {
    KeySpec spec = new PBEKeySpec(password.toCharArray(), fromHex(salt), PBKDF2_ITERATIONS, HASH_SIZE);
    SecretKeyFactory secretKeyFactory = null;
    try {
      secretKeyFactory = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
      return toHex(secretKeyFactory.generateSecret(spec).getEncoded());
    } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
      throw new UserManagementException("encryptPassword error.", e);
    }
  }

  private static byte[] fromHex (String hex) {
    byte[] binary = new byte[hex.length() / 2];
    for (int i = 0 ; i < binary.length ; i ++) {
      binary[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
    }

    return binary;
  }

  private static String toHex(byte[] array) {
    BigInteger bi = new BigInteger(1, array);
    String hex = bi.toString(16);
    int paddingLength = (array.length * 2) - hex.length();
    if (paddingLength > 0)
      return String.format("%0" + paddingLength + "d", 0) + hex;
    else
      return hex;
  }

  public String generateSalt() {
    SecureRandom random = null;
    try {
      random = SecureRandom.getInstance("SHA1PRNG");
    } catch (NoSuchAlgorithmException e) {
      throw new UserManagementException("generateSalt error.", e);
    }
    byte[] salt = new byte[SALT_SIZE];
    random.nextBytes(salt);
    return toHex(salt);
  }
}
