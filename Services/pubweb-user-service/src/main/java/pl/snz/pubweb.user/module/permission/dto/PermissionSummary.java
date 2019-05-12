package pl.snz.pubweb.user.module.permission.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PermissionSummary {
    private Long id;
    private String name;
    private String description;
    private String permissionKey;
}
