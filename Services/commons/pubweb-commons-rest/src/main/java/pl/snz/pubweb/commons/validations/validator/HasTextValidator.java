package pl.snz.pubweb.commons.validations.validator;

import lombok.RequiredArgsConstructor;
import pl.snz.pubweb.commons.validations.annotation.HasText;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class HasTextValidator implements ConstraintValidator<HasText, String> {

    private boolean nullable;

    @Override
    public void initialize(HasText constraintAnnotation) {
        nullable = constraintAnnotation.nullable();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null ? nullable : value.trim().length() > 0;
    }
}
