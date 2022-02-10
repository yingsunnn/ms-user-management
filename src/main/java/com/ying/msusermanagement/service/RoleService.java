package com.ying.msusermanagement.service;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.ying.msusermanagement.dto.PermissionDto;
import com.ying.msusermanagement.dto.RoleDto;
import com.ying.msusermanagement.entity.Role;
import com.ying.msusermanagement.repository.PermissionRepository;
import com.ying.msusermanagement.repository.RoleRepository;
import com.ying.msusermanagement.utils.OrikaMapperUtils;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

  public List<RoleDto> getUserRoles (String userId) {
    List<Role> roles = this.roleRepository.retrieveUserRoles(userId);

    if (CollectionUtils.isEmpty(roles)) {
      return ImmutableList.of();
    }

    List<RoleDto> roleDtos = OrikaMapperUtils.map(roles, RoleDto.class);

    roleDtos.forEach(
        roleDto ->
          roleDto.setPermissions(OrikaMapperUtils.map(
              this.permissionRepository.retrievePermission(roleDto.getId()),
              PermissionDto.class
          ))
    );

    return roleDtos;
  }

  public Set<PermissionDto> mapRolePermissionToPermission (List<RoleDto> roleDtos) {
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

  public Set<PermissionDto> getUserPermissions(String userId) {
    return ImmutableSet.of(
        PermissionDto.builder().id(1l).name("permission_1").build(),
        PermissionDto.builder().id(2l).name("permission_2").build()
    );
  }

  public void storeUserRoles (String userId, List<RoleDto> roles) {
    if (CollectionUtils.isEmpty(roles)) {
      return;
    }

    roles.forEach(
        role -> this.roleRepository.insertUserRole(userId, role.getId())
    );
  }
}
