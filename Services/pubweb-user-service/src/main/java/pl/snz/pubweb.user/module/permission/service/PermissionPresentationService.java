package pl.snz.pubweb.user.module.permission.service;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.permission.dto.PermissionSummary;
import pl.snz.pubweb.user.module.permission.model.Permission;
import pl.snz.pubweb.user.module.permission_acceptance.dto.AcceptedPermission;
import pl.snz.pubweb.user.module.permission_acceptance.UserPermissionAcceptance;

@Service
public class PermissionPresentationService {

    public PermissionSummary toSummary(Permission entity) {
        return PermissionSummary.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getText())
                .permissionKey(entity.getPermissionKey())
                .build();
    }

    public AcceptedPermission acceptedPermission(UserPermissionAcceptance upa) {
        return new AcceptedPermission(upa.getUser().getId(), upa.getPermission().getId(), upa.getPermission().getName(), upa.getPermission().getPermissionKey());
    }


}
