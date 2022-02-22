package com.ying.msusermanagement.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.ying.msusermanagement.dto.PermissionDto;
import com.ying.msusermanagement.dto.RoleDto;
import com.ying.msusermanagement.dto.UserDto;
import com.ying.msusermanagement.entity.Role;
import com.ying.msusermanagement.exception.DataNotExistException;
import com.ying.msusermanagement.repository.PermissionRepository;
import com.ying.msusermanagement.repository.RoleRepository;
import com.ying.msusermanagement.utils.OrikaMapperUtils;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

  private final RoleRepository roleRepository;
  private final PermissionRepository permissionRepository;

  public List<RoleDto> getUserRoles(Long userId) {
    List<Role> roles = this.roleRepository.retrieveUserRoles(userId);

    if (CollectionUtils.isEmpty(roles)) {
      return ImmutableList.of();
    }

    List<RoleDto> roleDtos = OrikaMapperUtils.map(roles, RoleDto.class);

    return this.getRolePermissions(roleDtos);
  }

  private List<RoleDto> getRolePermissions (List<RoleDto> roleDtos) {
    roleDtos.forEach(
        roleDto ->
            this.getRolePermissions(roleDto)
    );

    return roleDtos;
  }

  private RoleDto getRolePermissions (RoleDto roleDto) {
    return roleDto.setPermissions(OrikaMapperUtils.map(
        this.permissionRepository.retrievePermission(roleDto.getId()),
        PermissionDto.class
    ));
  }

  public Set<PermissionDto> mapRolePermissionToPermission(List<RoleDto> roleDtos) {
    if (CollectionUtils.isEmpty(roleDtos)) {
      return ImmutableSet.of();
    }

    Set<PermissionDto> permissionDtos = new HashSet();

    for (RoleDto roleDto : roleDtos) {
      if (!CollectionUtils.isEmpty(roleDto.getPermissions())) {
        for (PermissionDto permissionDto : roleDto.getPermissions()) {
          permissionDtos.add(permissionDto);
        }
      }
    }

    return permissionDtos;
  }

  public void storeUserRoles(
      Long userId,
      List<RoleDto> roles
  ) {
    if (CollectionUtils.isEmpty(roles)) {
      return;
    }

    roles.forEach(
        role -> this.roleRepository.insertUserRole(userId, role.getId())
    );
  }

  @Transactional
  public void updateUserRoles(
      Long userId,
      List<RoleDto> roles
  ) {
    this.roleRepository.delUserRoles(userId);
    this.storeUserRoles(userId, roles);
  }

  public List<RoleDto> getRoles() {
    List<RoleDto> roleDtos = OrikaMapperUtils.map(this.roleRepository.findAll(), RoleDto.class);

    return this.getRolePermissions(roleDtos);

  }

  @Transactional
  public RoleDto createRole(
      RoleDto roleDto,
      UserDto userDto
  ) {
    Date now = new Date();
    roleDto.setStatus("enabled")
    .setCreatedAt(now)
    .setUpdatedAt(now)
    .setCreatedBy(userDto.getId());

    Role savedRole = this.roleRepository.save(OrikaMapperUtils.map(roleDto, Role.class));
    roleDto.setId(savedRole.getId());

    this.storeRolePermissions(roleDto);

    return roleDto;
  }

  @Transactional
  public RoleDto updateRole (RoleDto roleDto, UserDto userDto) {
    Role role = this.roleRepository.findById(roleDto.getId()).orElseThrow(
        () -> new DataNotExistException("Role (" + roleDto.getId() + ") doesn't exist.")
    );

    if (Objects.nonNull(roleDto.getRoleName())) {
      role.setRoleName(role.getRoleName());
    }
    if (Objects.nonNull(roleDto.getDescription())) {
      role.setDescription(roleDto.getDescription());
    }
    if (Objects.nonNull(roleDto.getStatus())) {
      role.setStatus(roleDto.getStatus());
    }

    role.setUpdatedAt(Instant.now().toEpochMilli());

    this.roleRepository.save(role);

    this.updateRolePermissions(roleDto);

    return OrikaMapperUtils.map(role, RoleDto.class);
  }

  public RoleDto updateRolePermissions (RoleDto roleDto) {
    this.roleRepository.delRolePermissions(roleDto.getId());

    this.storeRolePermissions(roleDto);

    return roleDto;
  }

  @Transactional
  public RoleDto deleteRole (Long roleId, UserDto userDto) {
    Role role = this.roleRepository.findById(roleId).orElseThrow(
        () -> new DataNotExistException("Role (" + roleId + ") doesn't exist.")
    );

    this.roleRepository.delRolePermissions(roleId);
    this.roleRepository.deleteById(roleId);

    RoleDto roleDto = OrikaMapperUtils.map(role, RoleDto.class);

    return this.getRolePermissions(roleDto);
  }

  private void storeRolePermissions (RoleDto roleDto) {
    if (CollectionUtils.isEmpty(roleDto.getPermissions())) {
      return;
    }

    roleDto.getPermissions().forEach(
        permissionDto -> this.roleRepository.insertRolePermission(roleDto.getId(), permissionDto.getId())
    );
  }
}
