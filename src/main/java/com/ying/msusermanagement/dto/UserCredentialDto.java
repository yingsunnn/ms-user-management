package com.ying.msusermanagement.dto;

import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

@Data
@Builder
@Accessors(chain = true)
@NoArgsConstructor
@AllArgsConstructor
public class UserCredentialDto {

  private Long id;
  private String userId;
  private String credentialType;
  private String credentialId;
  private String password;
  private String salt;
  private String status;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
}
