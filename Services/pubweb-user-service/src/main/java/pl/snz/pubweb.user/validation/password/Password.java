package pl.snz.pubweb.user.validation.password;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = PasswordValidator.class)
public @interface Password {
    String message() default "{invalid.password.message}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
}
