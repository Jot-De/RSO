package pl.snz.pubweb.commons.validations.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.annotation.Annotation;

public abstract class NullableValidator<A extends Annotation,T> implements ConstraintValidator<A,T> {

    protected abstract boolean acceptNulls();
    protected abstract boolean validate(T value, ConstraintValidatorContext constraintValidatorContext);

    @Override
    public boolean isValid(T value, ConstraintValidatorContext context) {
        return value == null ? acceptNulls() : validate(value, context);
    }

}
