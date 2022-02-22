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
                          value =
                              "{\n"
                                  + "    \"id\": \"bade0968-d77c-480b-9031-72177aca66e1\",\n"
                                  + "    \"fullName\": \"Ying Sun 31\",\n"
                                  + "    \"email\": \"ying3@email.com\",\n"
                                  + "    \"gender\": \"male\",\n"
                                  + "    \"birthday\": \"2016-01-25T00:00:00.000+00:00\",\n"
                                  + "    \"createdAt\": \"2022-02-15T00:22:26.633+00:00\",\n"
                                  + "    \"updatedAt\": \"2022-02-15T05:32:29.776+00:00\",\n"
                                  + "    \"userCredentials\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 6,\n"
                                  + "            \"userId\": \"bade0968-d77c-480b-9031-72177aca66e1\",\n"
                                  + "            \"credentialType\": \"email\",\n"
                                  + "            \"credentialId\": \"ying3@email.com\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-15T00:22:26.653+00:00\",\n"
                                  + "            \"updatedAt\": \"2022-02-15T00:22:26.653+00:00\"\n"
                                  + "        }\n"
                                  + "    ],\n"
                                  + "    \"roles\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 1,\n"
                                  + "            \"roleName\": \"Super Admin\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-09T20:42:52.663+00:00\",\n"
                                  + "            \"updatedAt\": \"2022-02-09T20:42:52.663+00:00\",\n"
                                  + "            \"permissions\": [\n"
                                  + "                {\n"
                                  + "                    \"id\": 1,\n"
                                  + "                    \"name\": \"USERS_GET_USER\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 2,\n"
                                  + "                    \"name\": \"USERS_GET_ME\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 3,\n"
                                  + "                    \"name\": \"USERS_GET_USER_ROLES\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 4,\n"
                                  + "                    \"name\": \"USERS_GET_ME_ROLES\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 5,\n"
                                  + "                    \"name\": \"USERS_UPDATE_USER_ROLES\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 6,\n"
                                  + "                    \"name\": \"USERS_UPDATE_ME_ROLES\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 7,\n"
                                  + "                    \"name\": \"USERS_UPDATE_USER_PROFILE\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 8,\n"
                                  + "                    \"name\": \"USERS_UPDATE_ME_PROFILE\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 9,\n"
                                  + "                    \"name\": \"USERS_CHANGE_ME_PASSWORD\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 10,\n"
                                  + "                    \"name\": \"USERS_CHANGE_USER_PASSWORD\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 11,\n"
                                  + "                    \"name\": \"USERS_GET_USERS\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 12,\n"
                                  + "                    \"name\": \"ROLES_GET_ROLES\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 13,\n"
                                  + "                    \"name\": \"ROLES_CREATE_ROLE\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 14,\n"
                                  + "                    \"name\": \"ROLES_UPDATE_ROLE\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 15,\n"
                                  + "                    \"name\": \"ROLES_DELETE_ROLE\"\n"
                                  + "                }\n"
                                  + "            ]\n"
                                  + "        }\n"
                                  + "    ]\n"
                                  + "}")
                  }
              )
          )
      })
  @GetMapping("/{userId}")
  @Permissions({"USERS_GET_USER"})
  public UserDto getUser(
      @PathVariable("userId") Long userId,
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
                          value =
                              "{\n"
                                  + "    \"id\": \"4dc3e46e-5dff-4b4d-89b8-668a5e11d7f2\",\n"
                                  + "    \"fullName\": \"Ying Sun\",\n"
                                  + "    \"email\": \"ying@email.com\",\n"
                                  + "    \"gender\": \"male\",\n"
                                  + "    \"birthday\": \"2016-01-25T00:00:00\",\n"
                                  + "    \"createdAt\": \"2022-02-10T01:23:08.253\",\n"
                                  + "    \"updatedAt\": \"2022-02-10T01:23:08.253\",\n"
                                  + "    \"userCredentials\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 4,\n"
                                  + "            \"userId\": \"4dc3e46e-5dff-4b4d-89b8-668a5e11d7f2\",\n"
                                  + "            \"credentialType\": \"email\",\n"
                                  + "            \"credentialId\": \"ying@email.com\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-10T01:23:08.269\",\n"
                                  + "            \"updatedAt\": \"2022-02-10T01:23:08.269\"\n"
                                  + "        }\n"
                                  + "    ],\n"
                                  + "    \"roles\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 1,\n"
                                  + "            \"roleName\": \"Super Admin\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"permissions\": [\n"
                                  + "                {\n"
                                  + "                    \"id\": 1,\n"
                                  + "                    \"name\": \"USERS_GET_USER\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 2,\n"
                                  + "                    \"name\": \"USERS_GET_ME\"\n"
                                  + "                }\n"
                                  + "            ]\n"
                                  + "        },\n"
                                  + "        {\n"
                                  + "            \"id\": 2,\n"
                                  + "            \"roleName\": \"End User\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"createdBy\": \"4dc3e46e-5dff-4b4d-89b8-668a5e11d7f2\",\n"
                                  + "            \"permissions\": [\n"
                                  + "                {\n"
                                  + "                    \"id\": 2,\n"
                                  + "                    \"name\": \"USERS_GET_ME\"\n"
                                  + "                }\n"
                                  + "            ]\n"
                                  + "        }\n"
                                  + "    ]\n"
                                  + "}")
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
                          value =
                              "{\n"
                                  + "    \"id\": \"6ce4325e-1e5e-47d4-bc15-d8d108dd882a\",\n"
                                  + "    \"fullName\": \"Ying Sun\",\n"
                                  + "    \"email\": \"ying2@email.com\",\n"
                                  + "    \"gender\": \"male\",\n"
                                  + "    \"birthday\": \"2016-01-25T00:00:00\",\n"
                                  + "    \"createdAt\": \"2022-02-10T15:34:20.222\",\n"
                                  + "    \"updatedAt\": \"2022-02-10T15:34:20.222\",\n"
                                  + "    \"userCredentials\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 5,\n"
                                  + "            \"userId\": \"6ce4325e-1e5e-47d4-bc15-d8d108dd882a\",\n"
                                  + "            \"credentialType\": \"email\",\n"
                                  + "            \"credentialId\": \"ying2@email.com\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-10T15:34:20.383\",\n"
                                  + "            \"updatedAt\": \"2022-02-10T15:34:20.383\"\n"
                                  + "        }\n"
                                  + "    ],\n"
                                  + "    \"roles\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 1\n"
                                  + "        },\n"
                                  + "        {\n"
                                  + "            \"id\": 2\n"
                                  + "        }\n"
                                  + "    ]\n"
                                  + "}")
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
                      value = "{\n"
                          + "    \"fullName\": \"Ying Sun\",\n"
                          + "    \"email\": \"ying2@email.com\",\n"
                          + "    \"gender\": \"male\",\n"
                          + "    \"birthday\": \"2016-01-25T00:00:00\",\n"
                          + "    \"userCredentials\": [\n"
                          + "        {\n"
                          + "            \"credentialType\": \"email\",\n"
                          + "            \"credentialId\": \"ying2@email.com\",\n"
                          + "            \"password\": \"123456\"\n"
                          + "        }\n"
                          + "    ],\n"
                          + "    \"roles\": [\n"
                          + "        {\n"
                          + "            \"id\": 1\n"
                          + "        },\n"
                          + "        {\n"
                          + "            \"id\": 2\n"
                          + "        }\n"
                          + "    ]\n"
                          + "}")
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
                          value =
                              "{\n"
                                  + "    \"id\": \"4dc3e46e-5dff-4b4d-89b8-668a5e11d7f2\",\n"
                                  + "    \"fullName\": \"Ying Sun\",\n"
                                  + "    \"email\": \"ying@email.com\",\n"
                                  + "    \"gender\": \"male\",\n"
                                  + "    \"birthday\": \"2016-01-25T00:00:00\",\n"
                                  + "    \"createdAt\": \"2022-02-10T01:23:08.253\",\n"
                                  + "    \"updatedAt\": \"2022-02-10T01:23:08.253\",\n"
                                  + "    \"userCredentials\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 4,\n"
                                  + "            \"userId\": \"4dc3e46e-5dff-4b4d-89b8-668a5e11d7f2\",\n"
                                  + "            \"credentialType\": \"email\",\n"
                                  + "            \"credentialId\": \"ying@email.com\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-10T01:23:08.269\",\n"
                                  + "            \"updatedAt\": \"2022-02-10T01:23:08.269\"\n"
                                  + "        }\n"
                                  + "    ],\n"
                                  + "    \"roles\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 1,\n"
                                  + "            \"roleName\": \"Super Admin\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"permissions\": [\n"
                                  + "                {\n"
                                  + "                    \"id\": 1,\n"
                                  + "                    \"name\": \"USERS_GET_USER\"\n"
                                  + "                },\n"
                                  + "                {\n"
                                  + "                    \"id\": 2,\n"
                                  + "                    \"name\": \"USERS_GET_ME\"\n"
                                  + "                }\n"
                                  + "            ]\n"
                                  + "        },\n"
                                  + "        {\n"
                                  + "            \"id\": 2,\n"
                                  + "            \"roleName\": \"End User\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "            \"createdBy\": \"4dc3e46e-5dff-4b4d-89b8-668a5e11d7f2\",\n"
                                  + "            \"permissions\": [\n"
                                  + "                {\n"
                                  + "                    \"id\": 2,\n"
                                  + "                    \"name\": \"USERS_GET_ME\"\n"
                                  + "                }\n"
                                  + "            ]\n"
                                  + "        }\n"
                                  + "    ],\n"
                                  + "    \"accessToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb20ueWluZy51c2VyLW1hbmFnZW1lbnQiLCJVU0VSX0lEIjoiNGRjM2U0NmUtNWRmZi00YjRkLTg5YjgtNjY4YTVlMTFkN2YyIiwiQ1JFREVOVElBTF9JRCI6InlpbmdAZW1haWwuY29tIiwiQ1JFREVOVElBTF9UWVBFIjoiZW1haWwiLCJleHAiOjE2NDQ1NjEyNDV9.mc8vxHRL35YIeNzZOdK7hEyy1gNwVLpreDDbPSacQKM\"\n"
                                  + "}")
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
                      value =
                          "{\n"
                          + "    \"credentialType\": \"email\",\n"
                          + "    \"credentialId\": \"ying@email.com\",\n"
                          + "    \"password\": \"123456\"\n"
                          + "}")
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
                          value =
                              "[\n"
                                  + "    {\n"
                                  + "        \"id\": 1,\n"
                                  + "        \"roleName\": \"Super Admin\",\n"
                                  + "        \"status\": \"enabled\",\n"
                                  + "        \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"permissions\": [\n"
                                  + "            {\n"
                                  + "                \"id\": 1,\n"
                                  + "                \"name\": \"USERS_GET_USER\"\n"
                                  + "            },\n"
                                  + "            {\n"
                                  + "                \"id\": 2,\n"
                                  + "                \"name\": \"USERS_GET_ME\"\n"
                                  + "            }\n"
                                  + "        ]\n"
                                  + "    },\n"
                                  + "    {\n"
                                  + "        \"id\": 2,\n"
                                  + "        \"roleName\": \"End User\",\n"
                                  + "        \"status\": \"enabled\",\n"
                                  + "        \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"createdBy\": \"e826e938-5362-4779-9707-bd63db20d9b4\",\n"
                                  + "        \"permissions\": [\n"
                                  + "            {\n"
                                  + "                \"id\": 2,\n"
                                  + "                \"name\": \"USERS_GET_ME\"\n"
                                  + "            }\n"
                                  + "        ]\n"
                                  + "    }\n"
                                  + "]")
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
                          value =
                              "[\n"
                                  + "    {\n"
                                  + "        \"id\": 1,\n"
                                  + "        \"roleName\": \"Super Admin\",\n"
                                  + "        \"status\": \"enabled\",\n"
                                  + "        \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"permissions\": [\n"
                                  + "            {\n"
                                  + "                \"id\": 1,\n"
                                  + "                \"name\": \"USERS_GET_USER\"\n"
                                  + "            },\n"
                                  + "            {\n"
                                  + "                \"id\": 2,\n"
                                  + "                \"name\": \"USERS_GET_ME\"\n"
                                  + "            }\n"
                                  + "        ]\n"
                                  + "    },\n"
                                  + "    {\n"
                                  + "        \"id\": 2,\n"
                                  + "        \"roleName\": \"End User\",\n"
                                  + "        \"status\": \"enabled\",\n"
                                  + "        \"createdAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"updatedAt\": \"2022-02-09T20:42:52.663\",\n"
                                  + "        \"createdBy\": \"e826e938-5362-4779-9707-bd63db20d9b4\",\n"
                                  + "        \"permissions\": [\n"
                                  + "            {\n"
                                  + "                \"id\": 2,\n"
                                  + "                \"name\": \"USERS_GET_ME\"\n"
                                  + "            }\n"
                                  + "        ]\n"
                                  + "    }\n"
                                  + "]")
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
                      value =
                          "[\n"
                              + "    {\n"
                              + "        \"id\": 2\n"
                              + "    }\n"
                              + "]")
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
                      value =
                          "[\n"
                              + "    {\n"
                              + "        \"id\": 2\n"
                              + "    }\n"
                              + "]")
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
              responseCode = "200"
          )
      })
  @PutMapping("/{userId}")
  @Permissions({"USERS_UPDATE_USER_PROFILE"})
  public UserDto updateUserProfile (
      @PathVariable("userId") Long userId,
      @RequestBody UserDto userDto) {
    return this.userService.updateUserProfile(userId, userDto);
  }

  @Operation(tags = "User",
      summary = "Update me profile",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200"
          )
      })
  @PutMapping("/me")
  @Permissions({"USERS_UPDATE_ME_PROFILE"})
  public UserDto updateMeProfile (
      @AuthenticatedUser UserDto authenticatedUserDto,
      @RequestBody UserDto userDto) {
    return this.userService.updateUserProfile(authenticatedUserDto.getId(), userDto);
  }

  @Operation(tags = "User Credential",
      summary = "Change me password",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200"
          )
      })
  @PutMapping("/me/password")
  @Permissions({"USERS_CHANGE_ME_PASSWORD"})
  public void changeMePassword (
      @AuthenticatedUser UserDto authenticatedUserDto,
      @RequestBody UserCredentialDto userCredentialDto
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
              responseCode = "200"
          )
      })
  @PutMapping("/{userId}/password")
  @Permissions({"USERS_CHANGE_USER_PASSWORD"})
  public void changeUserPassword(
      @PathVariable("userId") Long userId,
      @AuthenticatedUser UserDto authenticatedUserDto,
      @RequestBody UserCredentialDto userCredentialDto
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
              responseCode = "200"
          )
      })
  @GetMapping("")
  @Permissions({"USERS_GET_USERS"})
  public List<UserDto> getUsers () {
    return this.userService.getUsers();
  }

  // reset password
}
