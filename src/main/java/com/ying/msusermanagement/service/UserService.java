package com.ying.msusermanagement.service;

import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.dto.converter.OrikaLongLocalDateTimeConverter;
import com.ying.msusermanagement.dto.converter.OrikaStringUUIDConverter;
import com.ying.msusermanagement.entity.User;
import com.ying.msusermanagement.repository.UserRepository;
import com.ying.msusermanagement.utils.OrikaMapperUtils;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.Converter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

  private static final List<Converter> ORIKA_CONVERTERS = List.of(
      new OrikaLongLocalDateTimeConverter(),
      new OrikaStringUUIDConverter());

  public UserDto createUser (UserDto userDto) {

    LocalDateTime now = LocalDateTime.now();
    userDto.setCreatedAt(now);
    userDto.setUpdatedAt(now);

    User user = this.userRepository.save(OrikaMapperUtils.map(userDto, User.class, ORIKA_CONVERTERS));
    return OrikaMapperUtils.map(user, UserDto.class, ORIKA_CONVERTERS);
  }
}
