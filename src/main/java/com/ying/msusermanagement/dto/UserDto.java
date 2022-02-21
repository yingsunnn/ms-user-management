package com.ying.msusermanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
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
public class UserDto {

  private String id;

  private String fullName;

  private String email;

  private String gender;

  private Date birthday;

  private Date createdAt;

  private Date updatedAt;

  private String createdBy;

  private List<UserCredentialDto> userCredentials;

  private List<RoleDto> roles;

  private String accessToken;
}
