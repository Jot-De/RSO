package pl.snz.pubweb.user.module.user.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.snz.pubweb.user.validation.login.Login;
import pl.snz.pubweb.user.validation.password.Password;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Data
public class SignUpRequest {

    @NotNull @Length(min = 3, max = 20)
    private String displayName;
    @NotNull @Length(min = 8, max = 254) @Password
    private String password;
    @NotNull @Length(min = 4, max = 25) @Login
    private String login;

    @NotNull
    private Boolean rulesAcceptance;
    @NotNull
    private Boolean profilingAcceptance;
    @NotNull
    private Boolean informationProcessingAcceptance;
    @NotNull @AssertTrue(message = "age.confirmation.required")
    private Boolean ageConfirmation;

    @NotNull
    @Email
    private String email;

}
