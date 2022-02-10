package com.ying.msusermanagement.controller;

import com.ying.msusermanagement.service.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

  private RoleService roleService;

}
