package com.ying.msusermanagement.service;

import com.google.common.collect.ImmutableSet;
import com.ying.msusermanagement.dto.PermissionDto;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class RoleService {

  public void getUserRoles () {

  }

  public Set<PermissionDto> getUserPermissions(String userId) {
    return ImmutableSet.of(
        PermissionDto.builder().id(1l).name("permission_1").build(),
        PermissionDto.builder().id(2l).name("permission_2").build()
    );
  }
}
