package pl.snz.pubweb.user.validation.login;

import pl.snz.pubweb.user.validation.password.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = LoginValidator.class)
public @interface Login {
    String message() default "{invalid.login.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
