package com.ying.msusermanagement.dto.converter;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class OrikaLongLocalDateTimeConverter extends BidirectionalConverter<Long, LocalDateTime> {

  @Override
  public LocalDateTime convertTo(
      Long milliseconds,
      Type<LocalDateTime> type,
      MappingContext mappingContext
  ) {
    if (Objects.isNull(milliseconds)) {
      return null;
    }
    return LocalDateTime.ofInstant(Instant.ofEpochMilli(milliseconds), ZoneOffset.UTC);
  }

  @Override
  public Long convertFrom(
      LocalDateTime localDateTime,
      Type<Long> type,
      MappingContext mappingContext
  ) {
    if (Objects.isNull(localDateTime)) {
      return null;
    }

    return localDateTime.toInstant(ZoneOffset.UTC).toEpochMilli();
  }
}
