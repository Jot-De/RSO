package pl.snz.pubweb.user.module.permission.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PermissionStatusChangeRequest {
    @NotNull
    private Long permissionId;
    @NotNull
    private boolean accept;
}
