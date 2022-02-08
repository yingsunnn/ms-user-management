package com.ying.msusermanagement.utils;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import org.junit.jupiter.api.Test;

class PBKDF2UtilsTest {

  @Test
  public void test () throws NoSuchAlgorithmException, InvalidKeySpecException {
    PBKDF2Utils pbkdf2Utils = new PBKDF2Utils();

    String salt = pbkdf2Utils.generateSalt();
    System.out.println("salt: " + salt);

    System.out.println(pbkdf2Utils.encryptPassword("123456", salt));

    System.out.println(pbkdf2Utils.authenticate(
        "1234561",
        "7479c5570320917e2a9611ca0207eebc4c496c3993d372bec62f145fe48427a520ed2bf873e74d8ade25641d1b9877b68c0e77f581e83c4dbb8fbffe1c431655a6b9adb91a15eff53f6182ce232e5f13a98cac09fcbb1c076ee900331f41cf49617e52ca2572ac11020a797333e15ec2f6ab30a816047554eff0a09b1c612dba",
        "a71b56507e004c8b15b18dd94aca33eb73e966b35717d6cd3e30684b89943472da723009809a6b8815484ebd60befa7507d4f09d565cc953bf1c2d5c3d454ef6"
    ));
  }

}