package com.ying.msusermanagement.utils;

import java.util.Objects;
import java.util.UUID;

public class ObjectUtils {

  public static String uuidToString (UUID uuid) {
    if (Objects.isNull(uuid)) {
      return null;
    }

    return uuid.toString();
  }

  public static UUID stringToUUID (String uuidStr) {
    if (Objects.isNull(uuidStr)) {
      return null;
    }

    return UUID.fromString(uuidStr);
  }

}
