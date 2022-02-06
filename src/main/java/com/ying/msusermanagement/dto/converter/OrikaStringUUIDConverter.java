package com.ying.msusermanagement.dto.converter;

import java.util.Objects;
import java.util.UUID;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class OrikaStringUUIDConverter extends BidirectionalConverter<String, UUID> {

  @Override
  public UUID convertTo(
      String uuidStr,
      Type<UUID> type,
      MappingContext mappingContext
  ) {
    if (Objects.isNull(uuidStr)) {
      return null;
    }

    return UUID.fromString(uuidStr);
  }

  @Override
  public String convertFrom(
      UUID uuid,
      Type<String> type,
      MappingContext mappingContext
  ) {
    if (Objects.isNull(uuid)) {
      return null;
    }

    return uuid.toString();
  }
}
