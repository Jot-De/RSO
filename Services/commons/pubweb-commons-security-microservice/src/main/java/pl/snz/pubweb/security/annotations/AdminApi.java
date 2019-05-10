package pl.snz.pubweb.security.annotations;

import org.springframework.security.access.annotation.Secured;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Secured("ADMIN")
public @interface AdminApi {}
