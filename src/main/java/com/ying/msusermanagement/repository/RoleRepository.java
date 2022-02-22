package com.ying.msusermanagement.repository;

import com.ying.msusermanagement.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

  @Query(
      value = " select r.* \n "
          + " from role r, user_role ur \n "
          + " where r.id = ur.role_id \n "
          + " and ur.user_id = :userId",
      nativeQuery = true)
  List<Role> retrieveUserRoles (@Param("userId") Long userId);

  @Modifying
  @Query(value = "insert into user_role (user_id, role_id) values (:userId, :roleId)",
      nativeQuery = true)
  void insertUserRole(Long userId, Long roleId);

  @Modifying
  @Query(value = "delete from user_role where user_id = :userId",
     nativeQuery = true)
  void delUserRoles (Long userId);

  @Modifying
  @Query(value = "insert into role_permission (role_id, permission_id) values (:roleId, :permissionId)",
      nativeQuery = true)
  void insertRolePermission (Long roleId, Long permissionId);

  @Modifying
  @Query(value = "delete from role_permission where role_id = :roleId",
      nativeQuery = true)
  void delRolePermissions (Long roleId);
}
