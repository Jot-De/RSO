package pl.snz.pubweb.user.container.aop;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface RequiresPermission {
    String[] permissionKeys();
}