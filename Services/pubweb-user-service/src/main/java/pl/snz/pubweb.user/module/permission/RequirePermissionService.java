package pl.snz.pubweb.user.module.permission;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.user.module.permission.model.Permission;
import pl.snz.pubweb.user.module.permission_acceptance.UserPermissionAcceptance;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PermissionService {

    private final RequestSecurityContextProvider requestSecurityContextProvider;

    public void requirePermissions(String[] requiredKeys) {
        final List<String> userPermissionKeys = requestSecurityContextProvider.getPrincipal().getAcceptedPermissions().stream()
                .map(UserPermissionAcceptance::getPermission)
                .map(Permission::getPermissionKey)
                .collect(Collectors.toList());

        final boolean valid = Arrays.stream(requiredKeys).allMatch(userPermissionKeys::contains);

        if(!valid) {
            throw constructException(requiredKeys);
        }
    }

    private BadRequestException constructException(String[] requiredKeys) {
        BadRequestException.BadRequestExceptionBuilder builder = BadRequestException.builder("permissions.not.satisfied");
        for(String requiredKey: requiredKeys)
            builder.error("permission.key", requiredKey);
        return builder.build();
    }

}
