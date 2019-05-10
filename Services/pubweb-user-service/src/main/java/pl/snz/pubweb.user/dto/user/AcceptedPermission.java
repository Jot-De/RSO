package pl.snz.pubweb.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class AcceptedPermission {
    Long userId;
    Long permissionId;
    String permissionName;
    String permissionKey;
}
