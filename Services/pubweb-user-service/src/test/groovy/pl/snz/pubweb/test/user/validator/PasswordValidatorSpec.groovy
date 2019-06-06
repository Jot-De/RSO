package pl.snz.pubweb.test.user.validator

import org.springframework.beans.factory.annotation.Autowired
import pl.snz.pubweb.commons.validations.ValidationPredicatesProvider
import pl.snz.pubweb.user.validation.password.PasswordValidator
import spock.lang.Specification

class PasswordValidatorSpec extends Specification {

    @Autowired
    private PasswordValidator passwordValidator = new PasswordValidator(new ValidationPredicatesProvider())

    def 'test validator'() {
        expect:
        passwordValidator.isValid(pass, null) == expected

        where:
        pass        | expected
        'qQ1!23456' | true
    }
}
