package com.ying.msusermanagement.service;

import com.google.common.collect.ImmutableMap;
import com.ying.msusermanagement.dto.UserCredentialDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.dto.converter.OrikaLongLocalDateTimeConverter;
import com.ying.msusermanagement.dto.converter.OrikaStringUUIDConverter;
import com.ying.msusermanagement.entity.User;
import com.ying.msusermanagement.entity.UserCredential;
import com.ying.msusermanagement.repository.UserCredentialRepository;
import com.ying.msusermanagement.repository.UserRepository;
import com.ying.msusermanagement.utils.OrikaMapperUtils;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import ma.glasnost.orika.Converter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;
  private UserCredentialRepository userCredentialRepository;

  private static final List<Converter> ORIKA_CONVERTERS = List.of(
      new OrikaLongLocalDateTimeConverter(),
      new OrikaStringUUIDConverter());

  @Transactional
  public UserDto createUser (UserDto userDto) {

    final LocalDateTime now = LocalDateTime.now();
    userDto.setCreatedAt(now);
    userDto.setUpdatedAt(now);



    User user = OrikaMapperUtils.map(userDto, User.class, ORIKA_CONVERTERS);
    List<UserCredential> userCredentials = OrikaMapperUtils.map(userDto.getUserCredentials(), UserCredential.class, ORIKA_CONVERTERS);

    User createdUser = this.userRepository.save(user);

    userDto.getUserCredentials().forEach(
        userCredentialDto ->
            userCredentialDto.setCreatedAt(now)
                .setUpdatedAt(now)
                .setSalt("123")
                .setUserId(user.getId())
                .setStatus("Enabled")
    );
    this.userCredentialRepository.saveAll(
        OrikaMapperUtils.map(userDto.getUserCredentials(), UserCredential.class, ORIKA_CONVERTERS));

    return OrikaMapperUtils.map(createdUser, UserDto.class, ORIKA_CONVERTERS);
  }

}
