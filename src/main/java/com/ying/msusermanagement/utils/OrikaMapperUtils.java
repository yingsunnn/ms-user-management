package com.ying.msusermanagement.utils;

import com.google.common.collect.ImmutableList;
import com.ying.msusermanagement.dto.converter.OrikaLongLocalDateTimeConverter;
import com.ying.msusermanagement.dto.converter.OrikaStringUUIDConverter;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import ma.glasnost.orika.Converter;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

public class OrikaMapperUtils {

  private OrikaMapperUtils() {

  }

  public static <S, D> D map (S source, Class<D> distClass) {
    return map(source, distClass, List.of(new OrikaLongLocalDateTimeConverter(), new OrikaStringUUIDConverter()));
  }

  public static <S, D> List<D> map (Collection<S> collection, Class<D> distClass) {
    return map(collection, distClass, List.of(new OrikaLongLocalDateTimeConverter(), new OrikaStringUUIDConverter()));
  }

  public static <S, D> D map (S source, Class<D> distClass, List<Converter> converters) {
    if (Objects.isNull(source))
      return null;

    return getMapperFacade(source.getClass(), distClass, converters).map(source, distClass);
  }

  public static <S, D> D map (S source, Class<D> distClass, Map<Class, Class> mapClasses, List<Converter> converters) {
    if (Objects.isNull(source))
      return null;

    return getMapperFacade(mapClasses, converters).map(source, distClass);
  }

  public static <S, D> List<D> map (Collection<S> collection, Class<D> toClass, List<Converter> converters) {
    if (Objects.isNull(collection))
      return null;

    Optional<S> first = collection.stream().findFirst();
    if (first.isPresent()) {
      return getMapperFacade(first.get().getClass(), toClass, converters).mapAsList(collection, toClass);
    } else{
      return Collections.emptyList();
    }
  }

  private static <S, D> MapperFacade getMapperFacade (Class<S> sourceClass, Class<D> distClass, List<Converter> converters) {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
    converters.forEach(converter -> mapperFactory.getConverterFactory().registerConverter(converter));

    return mapperFactory.getMapperFacade();
  }

  private static <S, D> MapperFacade getMapperFacade (Map<Class, Class> mapClasses, List<Converter> converters) {
    MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

    mapClasses.forEach((source, dist) -> {
      mapperFactory.classMap(source, dist).register();
    });

    converters.forEach(converter -> mapperFactory.getConverterFactory().registerConverter(converter));

    return mapperFactory.getMapperFacade();
  }
}
