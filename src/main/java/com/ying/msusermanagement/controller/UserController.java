package com.ying.msusermanagement.controller;

import com.ying.msusermanagement.dto.RoleDto;
import com.ying.msusermanagement.dto.UserCredentialDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.permission.AuthenticatedUser;
import com.ying.msusermanagement.permission.Permissions;
import com.ying.msusermanagement.service.RoleService;
import com.ying.msusermanagement.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private UserService userService;
  private RoleService roleService;

  @Operation(tags = "User",
      summary = "Get user",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.GET_USER_RESPONSE_BODY)
                  }
              )
          )
      })
  @GetMapping("/{userId}")
  @Permissions({"USERS_GET_USER"})
  public UserDto getUser(
      @PathVariable("userId") Long userId,
      @Parameter(required = false)
      @AuthenticatedUser UserDto userDto) {
    log.debug("Get user " + userId);
    return this.userService.getUser(userId);
  }

  @Operation(tags = "User",
      summary = "Get user me",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.GET_USER_RESPONSE_BODY)
                  }
              )
          )
      })
  @GetMapping("/me")
  @Permissions({"USERS_GET_ME"})
  public UserDto getUserMe(
      @AuthenticatedUser UserDto userDto) {
    return this.userService.getUser(userDto.getId());
  }

  @Operation(tags = "User",
      summary = "Create new user with credentials",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.CREATE_USER_RESPONSE_BODY)
                  }
              )
          )
      })
  @PostMapping("")
  public UserDto createUser (
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = UserDto.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.CREATE_USER_REQUEST_BODY)
              }))
      @RequestBody UserDto user
  ) {
    return this.userService.createUser(user);
  }

  @Operation(tags = "User Credential",
      summary = "Login",
      description = "",
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.AUTHENTICATE_USER_RESPONSE_BODY)
                  }
              )
          )
      })
  @PostMapping("/authentication/{userId}")
  public UserDto authenticateUser (
      @PathVariable("userId") Long userId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = UserCredentialDto.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.AUTHENTICATE_USER_REQUEST_BODY)
              }))
      @RequestBody UserCredentialDto userCredentialDto) {
    return this.userService.authenticateUser(userId, userCredentialDto);
  }

  @Operation(tags = "User Credential",
      summary = "Check if user credential exist",
      description = "",
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value =
                              "{\n"
                                  + "    \"credentialExist\": true\n"
                                  + "}")
                  }
              )
          )
      })
  @GetMapping(value = "/credentials", produces = "application/json")
  public String getDuplicatedUserCredentials (
      @Parameter(
          examples = {
            @ExampleObject(
                name = "email",
                value= "email"
            )})
      @RequestParam("credentialType") String credentialType,
      @Parameter(
          examples = {
              @ExampleObject(
                  name = "ying@email.com",
                  value= "ying@email.com"
              )})
      @RequestParam("credentialId") String credentialId) {

    boolean credentialExist = this.userService.checkCredentialExists(
        UserCredentialDto.builder()
            .credentialType(credentialType)
            .credentialId(credentialId)
            .build()
    );

    return "{ \"credentialExist\": " + credentialExist + " }";
  }

  @Operation(tags = "User",
      summary = "Get user roles",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = List.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.GET_USER_ROLES_RESPONSE_BODY)
                  }
              )
          )
      })
  @GetMapping("/{userId}/roles")
  @Permissions({"USERS_GET_USER_ROLES"})
  public List<RoleDto> getUserRoles (@PathVariable("userId") Long userId) {
    return this.roleService.getUserRoles(userId);
  }

  @Operation(tags = "User",
      summary = "Get me roles",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = List.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.GET_USER_ROLES_RESPONSE_BODY)
                  }
              )
          )
      })
  @GetMapping("/me/roles")
  @Permissions({"USERS_GET_ME_ROLES"})
  public List<RoleDto> getMeRoles (
      @AuthenticatedUser UserDto authenticatedUserDto
  ) {
    return this.roleService.getUserRoles(authenticatedUserDto.getId());
  }

  @Operation(tags = "User",
      summary = "Update user roles",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "204"
          )
      })
  @PostMapping("/{userId}/roles")
  @Permissions({"USERS_UPDATE_USER_ROLES"})
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void updateUserRoles (
      @PathVariable("userId") Long userId,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = List.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.UPDATE_USER_ROLES_REQUEST_BODY)
              }))
      @RequestBody List<RoleDto> roles
  ) {
    this.roleService.updateUserRoles(userId, roles);
  }

  @Operation(tags = "User",
      summary = "Update user roles",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "204"
          )
      })
  @PostMapping("/me/roles")
  @Permissions({"USERS_UPDATE_ME_ROLES"})
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void updateMeRoles (
      @AuthenticatedUser UserDto authenticatedUserDto,
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = List.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.UPDATE_USER_ROLES_REQUEST_BODY)
              }))
      @RequestBody List<RoleDto> roles
  ) {
    this.roleService.updateUserRoles(authenticatedUserDto.getId(), roles);
  }

  @Operation(tags = "User",
      summary = "Update user profile",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.UPDATE_USER_PROFILE_RESPONSE_BODY)
                  }
              )
          )
      })
  @PutMapping("/{userId}")
  @Permissions({"USERS_UPDATE_USER_PROFILE"})
  public UserDto updateUserProfile (
      @PathVariable("userId") Long userId,
      @RequestBody
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = UserDto.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.UPDATE_USER_PROFILE_REQUEST_BODY)
              })) UserDto userDto) {
    return this.userService.updateUserProfile(userId, userDto);
  }

  @Operation(tags = "User",
      summary = "Update me profile",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = UserDto.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.UPDATE_USER_PROFILE_RESPONSE_BODY)
                  }
              )
          )
      })
  @PutMapping("/me")
  @Permissions({"USERS_UPDATE_ME_PROFILE"})
  public UserDto updateMeProfile (
      @AuthenticatedUser UserDto authenticatedUserDto,
      @RequestBody
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = UserDto.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.UPDATE_USER_PROFILE_REQUEST_BODY)
              })) UserDto userDto) {
    return this.userService.updateUserProfile(authenticatedUserDto.getId(), userDto);
  }

  @Operation(tags = "User Credential",
      summary = "Change me password",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "204"
          )
      })
  @PutMapping("/me/password")
  @Permissions({"USERS_CHANGE_ME_PASSWORD"})
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void changeMePassword (
      @AuthenticatedUser UserDto authenticatedUserDto,
      @RequestBody
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = UserCredentialDto.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.CHANGE_USER_PASSWORD_REQUEST_BODY)
              })) UserCredentialDto userCredentialDto
  ) {
    this.userService.changeUserPassword(authenticatedUserDto.getId(), userCredentialDto);
  }

  @Operation(tags = "User Credential",
      summary = "Change user password",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "204"
          )
      })
  @PutMapping("/{userId}/password")
  @Permissions({"USERS_CHANGE_USER_PASSWORD"})
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void changeUserPassword(
      @PathVariable("userId") Long userId,
      @AuthenticatedUser UserDto authenticatedUserDto,
      @RequestBody
      @io.swagger.v3.oas.annotations.parameters.RequestBody(
          description = "",
          required = true,
          content = @Content(
              schema = @Schema(implementation = UserCredentialDto.class),
              mediaType = "application/json",
              examples = {
                  @ExampleObject(
                      name = "Response properties",
                      value = SwaggerDocConstant.CHANGE_USER_PASSWORD_REQUEST_BODY)
              })) UserCredentialDto userCredentialDto
  ) {
    this.userService.changeUserPassword(userId, userCredentialDto);
  }

  @Operation(tags = "User",
      summary = "Get users",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200",
              content = @Content(
                  mediaType = "application/json",
                  schema = @Schema(implementation = List.class),
                  examples = {
                      @ExampleObject(
                          name = "Response properties",
                          value = SwaggerDocConstant.GET_USERS_RESPONSE_BODY)
                  }
              )
          )
      })
  @GetMapping("")
  @Permissions({"USERS_GET_USERS"})
  public List<UserDto> getUsers () {
    return this.userService.getUsers();
  }

  // reset password
}
