package pl.snz.pubweb.user.module.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.user.module.auth.dto.CheckAuthResponse;
import pl.snz.pubweb.user.module.role.Role;
import pl.snz.pubweb.user.module.auth.dto.JwtAuthenticationResponse;
import pl.snz.pubweb.user.module.auth.dto.LoginRequest;
import pl.snz.pubweb.user.module.user.UserRepository;
import pl.snz.pubweb.user.module.user.model.User;
import pl.snz.pubweb.user.security.JwtTokenProvider;
import pl.snz.pubweb.user.security.UserPrincipal;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @PostMapping("/signin")
    public JwtAuthenticationResponse signIn(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLoginOrEmail(),
                        loginRequest.getPassword()
                )
        );
        String jwt = jwtTokenProvider.generateToken(authentication);
        return JwtAuthenticationResponse.builder()
                .accessToken(jwt)
                .userId(((UserPrincipal)authentication.getPrincipal()).getId())
                .build();
    }


    @GetMapping("/check")
    public CheckAuthResponse checkAuth(@RequestHeader("Authorization") String authorization) {
        String jwt = authorization.substring("Bearer ".length());
        if(!jwtTokenProvider.validateToken(jwt))
            return CheckAuthResponse.invalid();
        final Long userId = jwtTokenProvider.getUserIdFromJWT(jwt);
        final User user = userRepository.findById(userId).orElseThrow(NotFoundException.userById(userId));

        return new CheckAuthResponse(true, userId, Mappers.set(Role::getName, user.getRoles()));
    }


}
