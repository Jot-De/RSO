package pl.snz.pubweb.user.dto.role;

import lombok.Data;

import java.util.List;

@Data
public class RolesAssignmentRequest {
    List<Long> roles;
}
