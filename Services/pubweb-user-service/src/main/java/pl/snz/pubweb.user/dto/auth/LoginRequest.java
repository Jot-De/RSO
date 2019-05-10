package pl.snz.pubweb.user.dto.auth;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank
    private String loginOrEmail;

    @NotBlank
    private String password;
}
