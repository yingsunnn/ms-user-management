package com.ying.msusermanagement.controller;

import com.ying.msusermanagement.dto.RoleDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.permission.AuthenticatedUser;
import com.ying.msusermanagement.permission.Permissions;
import com.ying.msusermanagement.service.RoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@AllArgsConstructor
@RequestMapping("/roles")
public class RoleController {

  private RoleService roleService;

  @Operation(tags = "Role",
      summary = "Get all roles",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200"
          )
      })
  @GetMapping("")
  @Permissions("ROLES_GET_ROLES")
  public List<RoleDto> getRoles () {
    return this.roleService.getRoles();
  }

  @Operation(tags = "Role",
      summary = "Create role",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200"
          )
      })
  @PostMapping("")
  @Permissions("ROLES_CREATE_ROLE")
  public RoleDto createRole (
      @RequestBody RoleDto roleDto,
      @AuthenticatedUser UserDto userDto
  ) {
    return this.roleService.createRole(roleDto, userDto);
  }

  @Operation(tags = "Role",
      summary = "Update role",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200"
          )
      })
  @PutMapping("/{roleId}")
  @Permissions("ROLES_UPDATE_ROLE")
  public RoleDto updateRole (
      @PathVariable("roleId") Long roleId,
      @RequestBody RoleDto roleDto,
      @AuthenticatedUser UserDto userDto
  ) {
    roleDto.setId(roleId);
    return this.roleService.updateRole(roleDto, userDto);
  }

  @Operation(tags = "Role",
      summary = "Delete role",
      description = "",
      security = { @SecurityRequirement(name = "bearer-key") },
      responses = {
          @ApiResponse(
              description = "Successful response",
              responseCode = "200"
          )
      })
  @DeleteMapping("/{roleId}")
  @Permissions("ROLES_DELETE_ROLE")
  public RoleDto deleteRole (
      @PathVariable("roleId") Long roleId,
      @AuthenticatedUser UserDto userDto
  ) {
    return this.roleService.deleteRole(roleId, userDto);
  }

}
