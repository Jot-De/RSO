package pl.snz.pubweb.user.validation.login;

import lombok.RequiredArgsConstructor;
import pl.snz.pubweb.user.validation.ValidationPredicatesProvider;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class LoginValidator implements ConstraintValidator<Login, String> {
    private final ValidationPredicatesProvider validationPredicatesProvider;
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return validationPredicatesProvider.allCharsWithingNumbersOrUpperOrLowerCase(value);
    }
}
