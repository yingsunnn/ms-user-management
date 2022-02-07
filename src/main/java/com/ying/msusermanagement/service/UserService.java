package com.ying.msusermanagement.service;

import com.ying.msusermanagement.dto.UserCredentialDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.entity.User;
import com.ying.msusermanagement.entity.UserCredential;
import com.ying.msusermanagement.exception.DataNotExistException;
import com.ying.msusermanagement.exception.DuplicatedDataException;
import com.ying.msusermanagement.repository.UserCredentialRepository;
import com.ying.msusermanagement.repository.UserRepository;
import com.ying.msusermanagement.utils.OrikaMapperUtils;
import com.ying.msusermanagement.utils.PBKDF2Utils;
import java.time.LocalDateTime;
import java.util.List;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private UserRepository userRepository;
  private UserCredentialRepository userCredentialRepository;
  private PBKDF2Utils pbkdf2Utils;

  @Transactional
  public UserDto createUser(UserDto userDto) {

    final LocalDateTime now = LocalDateTime.now();
    userDto.setCreatedAt(now);
    userDto.setUpdatedAt(now);

    // Store user profile and credentials
    User createdUser = this.userRepository.save(OrikaMapperUtils.map(userDto, User.class));
    List<UserCredentialDto> createdUserCredentialDtos = this.createUserCredentials(createdUser.getId().toString(), userDto.getUserCredentials());

    //
    UserDto createdUserDto = OrikaMapperUtils.map(createdUser, UserDto.class);
    createdUserDto.setUserCredentials(createdUserCredentialDtos);

    return createdUserDto;
  }

  private List<UserCredentialDto> createUserCredentials(
      String userId,
      List<UserCredentialDto> credentialDtos
  ) {
    if (CollectionUtils.isEmpty(credentialDtos)) {
      throw new DataNotExistException("Must provide user credential.");
    }

    final LocalDateTime now = LocalDateTime.now();

    credentialDtos.forEach(
        userCredentialDto -> {
          // Check if the credential has already existed
          List<UserCredential> existCredentials = this.userCredentialRepository.findByCredentialTypeAndCredentialId(
              userCredentialDto.getCredentialType(),
              userCredentialDto.getCredentialId()
          );

          if (CollectionUtils.isNotEmpty(existCredentials)) {
            throw new DuplicatedDataException(
                "User credential has already existed. Credential type: " + userCredentialDto.getCredentialType()
                    + " Credential id: " + userCredentialDto.getCredentialId());
          }

          // fulfill user credentials
          String salt = pbkdf2Utils.generateSalt();
          userCredentialDto.setCreatedAt(now)
              .setUpdatedAt(now)
              .setSalt(salt)
              .setPassword(pbkdf2Utils.encryptPassword(userCredentialDto.getPassword(), salt))
              .setUserId(userId)
              .setStatus(UserCredential.STATUS_ENABLED);
        }
    );

    List<UserCredential> storedUserCredentials = this.userCredentialRepository.saveAll(OrikaMapperUtils.map(credentialDtos, UserCredential.class));

    return OrikaMapperUtils.map(storedUserCredentials, UserCredentialDto.class);
  }

  public int checkCredentialExists (UserCredentialDto userCredentialDto) {
    List<UserCredential> existCredentials = this.userCredentialRepository.findByCredentialTypeAndCredentialId(
        userCredentialDto.getCredentialType(),
        userCredentialDto.getCredentialId()
    );

    return existCredentials.size();
  }
}
