package com.ying.msusermanagement.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
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
public class RoleDto {

  private Long id;
  private String roleName;
  private String description;
  private String status;
  private Date createdAt;
  private Date updatedAt;
  private Long createdBy;

  private List<PermissionDto> permissions;
}
