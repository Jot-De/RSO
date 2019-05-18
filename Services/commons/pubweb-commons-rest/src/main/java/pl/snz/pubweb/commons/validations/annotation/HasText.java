package pl.snz.pubweb.commons.validations.annotation;

import pl.snz.pubweb.commons.validations.validator.HasTextValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD})
@Constraint(validatedBy = {HasTextValidator.class})
public @interface HasText {
    String message() default "field.text.required";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default{};
    boolean nullable();
}
