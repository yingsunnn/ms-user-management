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
  @GetMapping("/{userId}")
  @Permissions({"USERS_GET_USER"})
  public UserDto getUser(
      @PathVariable("userId") String userId,
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
      @PathVariable("userId") String userId,
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
  public List<RoleDto> getUserRoles (@PathVariable("userId") String userId) {
    return this.roleService.getUserRoles(userId);
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
  @ResponseStatus(value = HttpStatus.NO_CONTENT)
  public void updateUserRoles (
      @PathVariable("userId") String userId,
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
}
