package com.ying.msusermanagement.entity;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity(name="user")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

  @Id
  @GeneratedValue
  private UUID id;

  @Column(name="full_name")
  private String fullName;

  @Column(name="email")
  private String email;

  @Column(name="gender")
  private String gender;

  @Column(name="birthday")
  private Long birthday;

  @Column(name="created_at")
  private Long createdAt;

  @Column(name="updated_at")
  private Long updatedAt;

  @Column(name="created_by")
  private UUID createdBy;
}
