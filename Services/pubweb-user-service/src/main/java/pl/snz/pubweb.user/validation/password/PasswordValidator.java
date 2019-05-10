package pl.snz.pubweb.user.validation.password;

import lombok.RequiredArgsConstructor;
import pl.snz.pubweb.user.validation.ValidationPredicatesProvider;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class PasswordValidator implements ConstraintValidator<Password, String> {
    private final ValidationPredicatesProvider vpr;

    @Override
    public boolean isValid(String pass, ConstraintValidatorContext context) {
        return vpr.containsLowerCase(pass) && vpr.containsUpperCase(pass) && vpr.containsSpecialChar(pass);
    }
}
