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
import java.time.Instant;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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

    Date now = new Date();
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
    this.roleService.storeUserRoles(createdUser.getId(), userDto.getRoles());

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

    credentialDtos.forEach(
        userCredentialDto -> {
          this.buildUserCredential(userId, userCredentialDto);
        }
    );

    List<UserCredential> storedUserCredentials = this.userCredentialRepository.saveAll(OrikaMapperUtils.map(credentialDtos,
        UserCredential.class));

    return OrikaMapperUtils.map(storedUserCredentials, UserCredentialDto.class);
  }

  private void buildUserCredential(
      String userId,
      UserCredentialDto userCredentialDto
  ) {
    // Check if the credential has already existed
    UserCredential userCredential = this.userCredentialRepository.findByCredentialTypeAndCredentialId(
        userCredentialDto.getCredentialType(),
        userCredentialDto.getCredentialId()
    ).orElse(null);
    if (Objects.nonNull(userCredential)) {
      throw new DuplicatedDataException(
          "User credential has already existed. Credential type: " + userCredentialDto.getCredentialType()
              + " Credential id: " + userCredentialDto.getCredentialId());
    }

    Date now = new Date();

    // fulfill user credentials
    String salt = pbkdf2Utils.generateSalt();
    userCredentialDto.setCreatedAt(now)
        .setUpdatedAt(now)
        .setSalt(salt)
        .setPassword(pbkdf2Utils.encryptPassword(userCredentialDto.getPassword(), salt))
        .setUserId(userId)
        .setStatus(UserCredential.STATUS_ENABLED);
  }

  public boolean checkCredentialExists(UserCredentialDto userCredentialDto) {
    return this.userCredentialRepository.findByCredentialTypeAndCredentialId(
        userCredentialDto.getCredentialType(),
        userCredentialDto.getCredentialId()
    ).isPresent();

  }

  public UserDto authenticateUser(
      Long userId,
      UserCredentialDto userCredentialDto
  ) {
    // retrieve user and user credential
    UserCredential userCredential = this.userCredentialRepository.findByCredentialTypeAndCredentialId(
        userCredentialDto.getCredentialType(),
        userCredentialDto.getCredentialId()
    ).orElseThrow(
        () -> new DataNotExistException("User credential doesn't exist. Credential type: " +
            userCredentialDto.getCredentialType() + " Credential id: " + userCredentialDto.getCredentialId())
    );

    UserDto userDto = this.getUserDtoProfile(userId);

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

  private List<UserCredentialDto> getUserCredentials(Long userId) {

    List<UserCredentialDto> userCredentialDtos = OrikaMapperUtils.map(
        this.userCredentialRepository.findByUserId(userId), UserCredentialDto.class);

    if (CollectionUtils.isEmpty(userCredentialDtos)) {
      throw new DataNotExistException("User credential doesn't exist.");
    }

    userCredentialDtos.forEach(
        userCredentialDto -> userCredentialDto.setPassword(null).setSalt(null)
    );

    return userCredentialDtos;
  }

  private UserDto getUserDtoProfile(Long userId) {
    return OrikaMapperUtils.map(
        this.getUserProfile(userId)
        , UserDto.class);
  }

  private User getUserProfile(Long userId) {
    return this.userRepository.findById(userId).orElseThrow(
            () -> new DataNotExistException("User (" + userId + ") profile doesn't exist."));
  }

  public UserDto getUser(Long userId) {
    return this.getUserDtoProfile(userId)
        .setUserCredentials(this.getUserCredentials(userId))
        .setRoles(this.roleService.getUserRoles(userId));
  }

  public UserDto getUserForPermissionVerification(Long userId) {
    return this.getUserDtoProfile(userId).setRoles(this.roleService.getUserRoles(userId));
  }

  public UserDto updateUserProfile(
      Long userId,
      UserDto userDto
  ) {
    User user = this.getUserProfile(userId);

    user.setUpdatedAt(Instant.now().toEpochMilli());

    if (Objects.nonNull(userDto.getFullName())) {
      user.setFullName(userDto.getFullName());
    }
    if (Objects.nonNull(userDto.getEmail())) {
      user.setEmail(userDto.getEmail());
    }
    if (Objects.nonNull(userDto.getGender())) {
      user.setGender(userDto.getGender());
    }
    if (Objects.nonNull(userDto.getBirthday())) {
      user.setBirthday(userDto.getBirthday().getTime());
    }

    this.userRepository.save(user);

    return OrikaMapperUtils.map(user, UserDto.class);
  }

  public void changeUserPassword(
      Long userId,
      UserCredentialDto userCredentialDto
  ) {

    // Check if the credential has already existed
    UserCredential userCredential = this.userCredentialRepository.findByCredentialTypeAndCredentialIdAndUserId(
        userCredentialDto.getCredentialType(),
        userCredentialDto.getCredentialId(),
        userId
    ).orElseThrow(() -> new DuplicatedDataException(
        "User credential doesn't exist. Credential type: " + userCredentialDto.getCredentialType()
            + " Credential id: " + userCredentialDto.getCredentialId())
    );

    // fulfill user credential
    String salt = pbkdf2Utils.generateSalt();
    userCredential
        .setUpdatedAt(Instant.now().toEpochMilli())
        .setSalt(salt)
        .setPassword(pbkdf2Utils.encryptPassword(userCredentialDto.getPassword(), salt));

    this.userCredentialRepository.save(userCredential);
  }

  public List<UserDto> getUsers() {
    return OrikaMapperUtils.map(this.userRepository.findAll(), UserDto.class);
  }
}
