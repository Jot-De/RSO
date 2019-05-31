package pl.snz.pubweb.user.container.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.user.module.permission.service.RequirePermissionService;
import pl.snz.pubweb.user.security.RequestSecurityContextProvider;

@Aspect
@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class RequirePermissionAspect {

    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final RequirePermissionService requirePermissionService;

    @Around(Pointcuts.REQUIRES_PERMISSION)
    public Object checkPermission(ProceedingJoinPoint pjp, RequiresPermission requiresPermission) throws Throwable {
        final String[] requiredKeys = requiresPermission.permissionKeys();
        requirePermissionService.requirePermissions(requiredKeys);

        return pjp.proceed();
    }



}
