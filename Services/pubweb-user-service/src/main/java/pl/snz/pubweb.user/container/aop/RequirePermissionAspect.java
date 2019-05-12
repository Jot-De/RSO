package pl.snz.pubweb.user.container.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.user.module.permission.model.Permission;
import pl.snz.pubweb.user.module.permission_acceptance.UserPermissionAcceptance;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequirePermissionAspect {

    private final RequestSecurityContextProvider requestSecurityContextProvider;

    @Around(Pointcuts.REQUIRES_PERMISSION)
    public Object checkPermission(ProceedingJoinPoint pjp, RequiresPermission requiresPermission) throws Throwable {
        final String[] requiredKeys = requiresPermission.permissionKeys();
        final List<String> userPermissionKeys = requestSecurityContextProvider.getPrincipal().getAcceptedPermissions().stream()
                .map(UserPermissionAcceptance::getPermission)
                .map(Permission::getPermissionKey)
                .collect(Collectors.toList());

        final boolean valid = Arrays.stream(requiredKeys).allMatch(userPermissionKeys::contains);

        if(valid)
            return pjp.proceed();
        else
            throw constructException(requiredKeys);
    }

    private BadRequestException constructException(String[] requiredKeys) {
        BadRequestException.BadRequestExceptionBuilder builder = BadRequestException.builder("permissions.not.satisfied");
        for(String requiredKey: requiredKeys)
            builder.error("permission.key", requiredKey);
        return builder.build();
    }

}
