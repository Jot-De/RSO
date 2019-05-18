package pl.snz.pubweb.commons.validations.validator;


import lombok.RequiredArgsConstructor;
import pl.snz.pubweb.commons.validations.ValidationPredicatesProvider;
import pl.snz.pubweb.commons.validations.annotation.Base64;

import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class Base64Validator extends NullableValidator<Base64, String> {

    private final ValidationPredicatesProvider provider;
    private boolean nullabe;

    @Override
    protected boolean acceptNulls() {
        return false;
    }

    @Override
    protected boolean validate(String value, ConstraintValidatorContext constraintValidatorContext) {
        return provider.isBase64(value);
    }

    @Override
    public void initialize(Base64 annotation) {
        this.nullabe = annotation.nullable();
    }
}
