package pl.snz.pubweb.user.container.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
/** Keeps common, reusable pointcuts, encapsulates changes for example during package refactoring */
public class Pointcuts {

    private static final String clazz = "pl.snz.pubweb.user.container.aop.Pointcuts";

    @Pointcut("execution(public * *(..))")
    public void publicMethodExecution() {}

    public static final String REQUIRES_PERMISSION = clazz + ".requiredPermissionAnotationPresent(requiresPermission)";

    @Pointcut("@annotation(requiresPermission)")
    public void requiredPermissionAnotationPresent(RequiresPermission requiresPermission) {}
}
