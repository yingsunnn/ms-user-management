package com.ying.msusermanagement.repository;

import com.ying.msusermanagement.entity.UserCredential;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

  List<UserCredential> findByCredentialTypeAndCredentialId (String credentialType, String credentialId);

  List<UserCredential> findByUserId (UUID userId);
}
