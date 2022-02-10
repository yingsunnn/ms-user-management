package com.ying.msusermanagement.repository;

import com.ying.msusermanagement.entity.Role;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
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

}
