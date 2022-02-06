package com.ying.msusermanagement.service;

import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.dto.mapper.UserMapper;
import com.ying.msusermanagement.entity.User;
import com.ying.msusermanagement.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;

  public UserDto createUser (UserDto userDto) {

    userDto.setCreatedAt(LocalDateTime.now());
    userDto.setUpdatedAt(LocalDateTime.now());

    User user = this.userRepository.save(UserMapper.userDtoToUser(userDto));

    return UserMapper.userToUserDto(user);
  }
}
