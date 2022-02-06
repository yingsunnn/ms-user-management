package com.ying.msusermanagement.dto;

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
public class UserDto {

  private String id;

  private String fullName;

  private String email;

  private String gender;

  private LocalDateTime birthday;

  private LocalDateTime createdAt;

  private LocalDateTime updatedAt;

  private String createdBy;
}
