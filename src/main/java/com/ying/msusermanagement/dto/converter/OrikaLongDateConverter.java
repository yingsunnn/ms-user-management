package com.ying.msusermanagement.dto.converter;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;
import ma.glasnost.orika.MappingContext;
import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

public class OrikaLongDateConverter extends BidirectionalConverter<Long, Date> {

  @Override
  public Date convertTo(
      Long aLong,
      Type<Date> type,
      MappingContext mappingContext
  ) {
    if (Objects.isNull(aLong)) {
      return null;
    }

    return new Date(aLong);
  }

  @Override
  public Long convertFrom(
      Date date,
      Type<Long> type,
      MappingContext mappingContext
  ) {
    if (Objects.isNull(date)) {
      return null;
    }

    return date.getTime();
  }
}
