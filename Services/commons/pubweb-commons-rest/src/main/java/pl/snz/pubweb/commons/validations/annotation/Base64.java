package pl.snz.pubweb.commons.validations.annotation;

import pl.snz.pubweb.commons.validations.validator.Base64Validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = {Base64Validator.class})
public @interface Base64 {
    String message() default "must.be.base64";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
    boolean nullable();
}
