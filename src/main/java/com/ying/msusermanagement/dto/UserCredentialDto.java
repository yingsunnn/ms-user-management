package com.ying.msusermanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
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
@JsonInclude(Include.NON_NULL)
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
