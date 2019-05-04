package pl.snz.pubweb.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.exception.AuthorizationException;
import pl.snz.pubweb.user.model.permission.Permission;
import pl.snz.pubweb.user.model.permission.UserPermissionAcceptance;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PermissionService {

    private final RequestSecurityContextProvider requestSecurityContextProvider;

    public void requirePermission(String permissionKey) throws AuthorizationException {
        requestSecurityContextProvider.getPrincipal().getAcceptedPermissions().stream()
                .map(UserPermissionAcceptance::getPermission)
                .map(Permission::getPermissionKey)
                .filter(key -> key.equals(permissionKey))
                .findFirst()
                .orElseThrow(() -> AuthorizationException.permissionRequired(permissionKey));
    }
}
