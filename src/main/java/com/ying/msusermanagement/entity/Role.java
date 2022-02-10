package com.ying.msusermanagement.entity;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Entity(name="role")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
public class Role {

  @Id
  @GeneratedValue
  private Long id;

  @Column(name="role_name")
  private String roleName;

  @Column(name="description")
  private String description;

  @Column(name="status")
  private String status;

  @Column(name="created_at")
  private Long createdAt;

  @Column(name="updated_at")
  private Long updatedAt;

  @Column(name="created_by")
  private UUID createdBy;

}
