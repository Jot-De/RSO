package pl.snz.pubweb.user.presentation;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.dto.permission.PermissionSummary;
import pl.snz.pubweb.user.dto.user.AcceptedPermission;
import pl.snz.pubweb.user.model.permission.Permission;
import pl.snz.pubweb.user.model.permission.UserPermissionAcceptance;

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
