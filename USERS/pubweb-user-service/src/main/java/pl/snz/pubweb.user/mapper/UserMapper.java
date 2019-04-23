package pl.snz.pubweb.user.mapper;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.dto.user.SignUpRequest;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.model.UserPersonalInformation;
import pl.snz.pubweb.user.model.display.UserDisplaySettings;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class UserMapper {
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public User fromSignUpRequest(SignUpRequest signUpRequest) {
        return User.builder()
                .login(signUpRequest.getLogin())
                .password(bCryptPasswordEncoder.encode(signUpRequest.getPassword()))
                .displayName(signUpRequest.getDisplayName())
                .email(signUpRequest.getEmail())
                .userDisplaySettings(UserDisplaySettings.allPrivate())
                .userPersonalInformation(UserPersonalInformation.none())
                .build();
    }

}
