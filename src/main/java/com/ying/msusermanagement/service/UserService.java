package com.ying.msusermanagement.service;

import com.google.common.collect.ImmutableList;
import com.ying.msusermanagement.dto.UserCredentialDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.entity.User;
import com.ying.msusermanagement.entity.UserCredential;
import com.ying.msusermanagement.exception.AuthenticationException;
import com.ying.msusermanagement.exception.DataNotExistException;
import com.ying.msusermanagement.exception.DuplicatedDataException;
import com.ying.msusermanagement.repository.UserCredentialRepository;
import com.ying.msusermanagement.repository.UserRepository;
import com.ying.msusermanagement.utils.OrikaMapperUtils;
import com.ying.msusermanagement.utils.PBKDF2Utils;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

  private final UserRepository userRepository;
  private final UserCredentialRepository userCredentialRepository;
  private final PBKDF2Utils pbkdf2Utils;
  private final JWTService jwtService;
  private final RoleService roleService;

  @Transactional
  public UserDto createUser(UserDto userDto) {

    final LocalDateTime now = LocalDateTime.now();
    userDto.setCreatedAt(now);
    userDto.setUpdatedAt(now);

    // Store user profile
    User createdUser = this.userRepository.save(OrikaMapperUtils.map(userDto, User.class));

    // Store credentials
    List<UserCredentialDto> createdUserCredentialDtos = this.createUserCredentials(createdUser.getId().toString(),
        userDto.getUserCredentials());
    createdUserCredentialDtos.forEach(
        userCredentialDto -> userCredentialDto.setPassword(null).setSalt(null)
    );

    // Store user role
    this.roleService.storeUserRoles(createdUser.getId().toString(), userDto.getRoles());

    // mapping
    UserDto createdUserDto = OrikaMapperUtils.map(createdUser, UserDto.class);
    createdUserDto
        .setUserCredentials(createdUserCredentialDtos)
        .setRoles(userDto.getRoles());

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
          if (CollectionUtils.isNotEmpty(this.userCredentialRepository.findByCredentialTypeAndCredentialId(
              userCredentialDto.getCredentialType(),
              userCredentialDto.getCredentialId()
          ))) {
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

    List<UserCredential> storedUserCredentials = this.userCredentialRepository.saveAll(OrikaMapperUtils.map(credentialDtos,
        UserCredential.class));

    return OrikaMapperUtils.map(storedUserCredentials, UserCredentialDto.class);
  }

  public boolean checkCredentialExists(UserCredentialDto userCredentialDto) {
    return CollectionUtils.isNotEmpty(this.userCredentialRepository.findByCredentialTypeAndCredentialId(
        userCredentialDto.getCredentialType(),
        userCredentialDto.getCredentialId()
    ));

  }

  public UserDto authenticateUser(
      String userId,
      UserCredentialDto userCredentialDto
  ) {
    // retrieve user and user credential
    UserCredential userCredential = this.userCredentialRepository.findByCredentialTypeAndCredentialId(
        userCredentialDto.getCredentialType(),
        userCredentialDto.getCredentialId()
    ).stream()
        .findFirst()
        .orElseThrow(
            () -> new DataNotExistException("User credential doesn't exist. Credential type: " +
                userCredentialDto.getCredentialType() + " Credential id: " + userCredentialDto.getCredentialId())
        );

    UserDto userDto = this.getUserProfile(userId);

    // verify password
    if (false == this.pbkdf2Utils.authenticate(userCredentialDto.getPassword(),
        userCredential.getPassword(),
        userCredential.getSalt())) {
      throw new AuthenticationException("User password incorrect.");
    }

    // generate jwt token
    String jwtToken = this.jwtService.generateJWTToken(
        userId,
        userCredential.getCredentialType(),
        userCredential.getCredentialId());

    UserCredentialDto returnUserCredentialDto = OrikaMapperUtils.map(userCredential, UserCredentialDto.class);
    returnUserCredentialDto.setPassword(null);
    returnUserCredentialDto.setSalt(null);

    userDto
        .setAccessToken(jwtToken)
        .setUserCredentials(ImmutableList.of(returnUserCredentialDto))
        .setRoles(this.roleService.getUserRoles(userId));

    return userDto;
  }

  private List<UserCredentialDto> getUserCredentials(String userId) {

    List<UserCredentialDto> userCredentialDtos = OrikaMapperUtils.map(
        this.userCredentialRepository.findByUserId(UUID.fromString(userId)), UserCredentialDto.class);

    if (CollectionUtils.isEmpty(userCredentialDtos)) {
      throw new DataNotExistException("User credential doesn't exist.");
    }

    userCredentialDtos.forEach(
        userCredentialDto -> userCredentialDto.setPassword(null).setSalt(null)
    );

    return userCredentialDtos;
  }

  private UserDto getUserProfile(String userId) {
    return OrikaMapperUtils.map(
        this.userRepository.findById(UUID.fromString(userId)).orElseThrow(
            () -> new DataNotExistException("User (" + userId + ") profile doesn't exist.")),
        UserDto.class);
  }

  public UserDto getUser(String userId) {
    return this.getUserProfile(userId)
        .setUserCredentials(this.getUserCredentials(userId))
        .setRoles(this.roleService.getUserRoles(userId));
  }

  public UserDto getUserForPermissionVerification(String userId) {
    return this.getUserProfile(userId).setRoles(this.roleService.getUserRoles(userId));
  }
}
