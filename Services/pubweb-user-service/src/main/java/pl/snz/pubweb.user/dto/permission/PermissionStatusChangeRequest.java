package pl.snz.pubweb.user.dto.permission;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PermissionStatusChangeRequest {
    @NotNull
    private Long permissionId;
    @NotNull
    private boolean accept;
}
