package com.ying.msusermanagement.dto;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
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
