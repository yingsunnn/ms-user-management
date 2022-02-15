package com.ying.msusermanagement.repository;

import com.ying.msusermanagement.entity.UserCredential;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserCredentialRepository extends JpaRepository<UserCredential, Long> {

  Optional<UserCredential> findByCredentialTypeAndCredentialId (String credentialType, String credentialId);

  Optional<UserCredential> findByCredentialTypeAndCredentialIdAndUserId (String credentialType, String credentialId, UUID userId);

  List<UserCredential> findByUserId (UUID userId);
}
