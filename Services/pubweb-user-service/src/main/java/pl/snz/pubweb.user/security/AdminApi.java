package pl.snz.pubweb.user.security;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured("ROLE_ADMIN")
public @interface AdminApi {}
