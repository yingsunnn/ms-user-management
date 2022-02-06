package com.ying.msusermanagement.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;

public class DateTimeUtils {

  public static Long localDateTimeToLong(LocalDateTime localDateTime) {
    if (Objects.isNull(localDateTime)) {
      return null;
    }

    return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
  }

  public static LocalDateTime longToLocalDateTime(Long milliseconds) {
    if (Objects.isNull(milliseconds)) {
      return null;
    }
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC);
  }

}
