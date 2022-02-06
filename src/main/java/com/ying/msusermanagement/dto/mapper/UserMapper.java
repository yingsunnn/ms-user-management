package com.ying.msusermanagement.dto.mapper;

import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.entity.User;
import com.ying.msusermanagement.utils.DateTimeUtils;
import com.ying.msusermanagement.utils.ObjectUtils;

public class UserMapper {

  public static UserDto userToUserDto (User user) {
    return UserDto.builder()
        .id(ObjectUtils.uuidToString(user.getId()))
        .fullName(user.getFullName())
        .email(user.getEmail())
        .gender(user.getGender())
        .birthday(DateTimeUtils.longToLocalDateTime(user.getBirthday()))
        .createdAt(DateTimeUtils.longToLocalDateTime(user.getCreatedAt()))
        .updatedAt(DateTimeUtils.longToLocalDateTime(user.getUpdatedAt()))
        .createdBy(ObjectUtils.uuidToString(user.getCreatedBy()))
        .build();
  }

  public static User userDtoToUser (UserDto userDto) {
    return User.builder()
        .id(ObjectUtils.stringToUUID(userDto.getId()))
        .fullName(userDto.getFullName())
        .email(userDto.getEmail())
        .gender(userDto.getGender())
        .birthday(DateTimeUtils.localDateTimeToLong(userDto.getBirthday()))
        .createdAt(DateTimeUtils.localDateTimeToLong(userDto.getCreatedAt()))
        .updatedAt(DateTimeUtils.localDateTimeToLong(userDto.getUpdatedAt()))
        .createdBy(ObjectUtils.stringToUUID(userDto.getCreatedBy()))
        .build();
  }
}
