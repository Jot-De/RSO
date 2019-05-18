package pl.snz.pubweb.security.annotations;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@PreAuthorize("hasAuthority('ADMIN')")
public @interface AdminApi {}
