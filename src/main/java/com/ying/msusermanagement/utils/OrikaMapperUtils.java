package com.ying.msusermanagement.utils;

import java.util.List;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaMapperUtils {

  private static final MapperFactory MAPPER_FACTORY = new DefaultMapperFactory.Builder().build();
  private static final MapperFacade MAPPER_FACADE = MAPPER_FACTORY.getMapperFacade();

  private OrikaMapperUtils() {

  }

  public static <S, D> D map(S source, Class<D> distClass) {
    return MAPPER_FACADE.map(source, distClass);
  }

  public static <S, D> List<D> mapAsList(Iterable<S> iterable, Class<D> toClass) {
    return MAPPER_FACADE.mapAsList(iterable, toClass);
  }

  public static <S, D> D map (S source, Class<D> distClass, List<Converter> converters) {
    return getMapperFacade(source.getClass(), distClass, converters).map(source, distClass);
  }

  private static <S, D> MapperFacade getMapperFacade (Class<S> sourceClass, Class<D> distClass, List<Converter> converters) {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    converters.forEach(converter -> mapperFactory.getConverterFactory().registerConverter(converter));

    return mapperFactory.getMapperFacade();
  }
}
