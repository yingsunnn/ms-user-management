package com.ying.msusermanagement.repository;

import com.ying.msusermanagement.entity.UserCredential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

}
