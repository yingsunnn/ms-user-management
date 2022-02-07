package com.ying.msusermanagement.controller;

import com.ying.msusermanagement.Permissions;
import com.ying.msusermanagement.dto.UserCredentialDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {

  private UserService userService;

  @GetMapping("/{userId}")
  @Permissions({"p1", "p2", "p3"})
  public String getUser(
      @PathVariable("userId") String userId,
      @RequestHeader("Authorization") String jwtToken) {
    return "userId: " + userId;
  }

  @PostMapping("")
  public UserDto createUser (
      @RequestBody UserDto user
  ) {
    return this.userService.createUser(user);
  }

  @GetMapping(value = "/credentials", produces = "application/json")
  public String getDuplicatedUserCredentials (
      @RequestParam("credentialType") String credentialType,
      @RequestParam("credentialId") String credentialId) {

    int count = this.userService.checkCredentialExists(
        UserCredentialDto.builder()
            .credentialType(credentialType)
            .credentialId(credentialId)
            .build()
    );

    if (count > 0) {
      return "{ \"credentialExist\": true }";
    } else {
      return "{ \"credentialExist\": false }";
    }
  }
}
