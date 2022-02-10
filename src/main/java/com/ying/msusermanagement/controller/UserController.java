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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private UserService userService;
  private RoleService roleService;

  @GetMapping("/{userId}")
  @Permissions({"USERS_GET_USER"})
  public UserDto getUser(
      @PathVariable("userId") String userId,
      @AuthenticatedUser UserDto userDto) {
    log.debug("Get user " + userId);
    return this.userService.getUser(userId);
  }

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
                                  + "    \"id\": \"e826e938-5362-4779-9707-bd63db20d9b4\",\n"
                                  + "    \"fullName\": \"Ying Sun\",\n"
                                  + "    \"email\": \"ying@email.com\",\n"
                                  + "    \"gender\": \"male\",\n"
                                  + "    \"birthday\": \"2016-01-25T00:00:00\",\n"
                                  + "    \"createdAt\": \"2022-02-07T02:05:27.228\",\n"
                                  + "    \"updatedAt\": \"2022-02-07T02:05:27.228\",\n"
                                  + "    \"createdBy\": null,\n"
                                  + "    \"userCredentials\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 2,\n"
                                  + "            \"userId\": \"e826e938-5362-4779-9707-bd63db20d9b4\",\n"
                                  + "            \"credentialType\": \"email\",\n"
                                  + "            \"credentialId\": \"ying@email.com\",\n"
                                  + "            \"password\": \"7479c5570320917e2a9611ca0207eebc4c496c3993d372bec62f145fe48427a520ed2bf873e74d8ade25641d1b9877b68c0e77f581e83c4dbb8fbffe1c431655a6b9adb91a15eff53f6182ce232e5f13a98cac09fcbb1c076ee900331f41cf49617e52ca2572ac11020a797333e15ec2f6ab30a816047554eff0a09b1c612dba\",\n"
                                  + "            \"salt\": \"a71b56507e004c8b15b18dd94aca33eb73e966b35717d6cd3e30684b89943472da723009809a6b8815484ebd60befa7507d4f09d565cc953bf1c2d5c3d454ef6\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-07T02:05:27.245\",\n"
                                  + "            \"updatedAt\": \"2022-02-07T02:05:27.245\"\n"
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
                          + "    \"email\": \"ying@email.com\",\n"
                          + "    \"gender\": \"male\",\n"
                          + "    \"birthday\": \"2016-01-25T00:00:00\",\n"
                          + "    \"userCredentials\": [\n"
                          + "        {\n"
                          + "            \"credentialType\": \"email\",\n"
                          + "            \"credentialId\": \"ying@email.com\",\n"
                          + "            \"password\": \"123456\"\n"
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
                                  + "    \"id\": \"e826e938-5362-4779-9707-bd63db20d9b4\",\n"
                                  + "    \"fullName\": \"Ying Sun\",\n"
                                  + "    \"email\": \"ying@email.com\",\n"
                                  + "    \"gender\": \"male\",\n"
                                  + "    \"birthday\": \"2016-01-25T00:00:00\",\n"
                                  + "    \"createdAt\": \"2022-02-07T02:05:27.228\",\n"
                                  + "    \"updatedAt\": \"2022-02-07T02:05:27.228\",\n"
                                  + "    \"userCredentials\": [\n"
                                  + "        {\n"
                                  + "            \"id\": 2,\n"
                                  + "            \"userId\": \"e826e938-5362-4779-9707-bd63db20d9b4\",\n"
                                  + "            \"credentialType\": \"email\",\n"
                                  + "            \"credentialId\": \"ying@email.com\",\n"
                                  + "            \"status\": \"enabled\",\n"
                                  + "            \"createdAt\": \"2022-02-07T02:05:27.245\",\n"
                                  + "            \"updatedAt\": \"2022-02-07T02:05:27.245\"\n"
                                  + "        }\n"
                                  + "    ],\n"
                                  + "    \"accessToken\": \"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjb20ueWluZy51c2VyLW1hbmFnZW1lbnQiLCJVU0VSX0lEIjoiRTgyNkU5MzgtNTM2Mi00Nzc5LTk3MDctQkQ2M0RCMjBEOUI0IiwiQ1JFREVOVElBTF9JRCI6InlpbmdAZW1haWwuY29tIiwiUk9MRV9JRFMiOiIiLCJDUkVERU5USUFMX1RZUEUiOiJlbWFpbCIsImV4cCI6MTY0NDM4Njk1Mn0.lefJMRxZVK5pRvUBr3g2EBs3J0CyAG992q52DXntnnI\"\n"
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
}
