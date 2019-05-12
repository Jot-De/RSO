package pl.snz.pubweb.user.module.role.dto;

import lombok.Data;

import java.util.List;

@Data
public class RolesAssignmentRequest {
    List<Long> roles;
}
