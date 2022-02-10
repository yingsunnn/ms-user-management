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
          + " and ur.user_id = UUID_TO_BIN(:userId)",
      nativeQuery = true)
  List<Role> retrieveUserRoles (@Param("userId") String userId);

  @Modifying
  @Query(value = "insert into user_role (user_id, role_id) values (UUID_TO_BIN(:userId), :roleId)",
      nativeQuery = true)
  void insertUserRole(String userId, Long roleId);

  @Modifying
  @Query(value = "delete from user_management.user_role where user_id = UUID_TO_BIN(:userId)",
     nativeQuery = true)
  void delUserRoles (String userId);
}
