package com.ying.msusermanagement.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name="user_credential")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class UserCredential {

  public static final String STATUS_ENABLED = "enabled";
  public static final String STATUS_DISABLED = "disabled";

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name="user_id")
  private UUID userId;

  @Column(name="credential_type")
  private String credentialType;

  @Column(name="credential_id")
  private String credentialId;

  @Column(name="password")
  private String password;

  @Column(name="salt")
  private String salt;

  @Column(name="status")
  private String status;

  @Column(name="created_at")
  private Long createdAt;

  @Column(name="updated_at")
  private Long updatedAt;
}
