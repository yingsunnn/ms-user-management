package com.ying.msusermanagement.repository;

import com.ying.msusermanagement.entity.Permission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

  @Query(
      value = "select p.*\n"
          + "from user_management.permission p,\n"
          + "     user_management.role_permission rp\n"
          + "where p.id = rp.permission_id\n"
          + "and role_id = :roleId",
      nativeQuery = true)
  List<Permission> retrievePermission (@Param("roleId") Long roleId);
}
