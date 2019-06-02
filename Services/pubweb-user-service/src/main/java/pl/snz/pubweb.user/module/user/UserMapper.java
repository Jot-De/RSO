package pl.snz.pubweb.user.module.user;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.user.module.user.dto.SignUpRequest;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.module.user.model.UserDisplaySettings;
import pl.snz.pubweb.user.module.user.model.UserPersonalInformation;

import java.util.HashSet;

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
                .acceptedPermissions(new HashSet<>())
                .build();
    }

}
