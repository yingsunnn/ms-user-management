package com.ying.msusermanagement.controller;

import com.ying.msusermanagement.Permissions;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @GetMapping("/{userId}")
  @Permissions({"p1", "p2", "p3"})
  public String getUser(
      @PathVariable("userId") String userId,
      @RequestHeader("Authorization") String jwtToken) {
    return "userId: " + userId;
  }
}
