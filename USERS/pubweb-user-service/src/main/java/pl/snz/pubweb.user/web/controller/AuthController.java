package pl.snz.pubweb.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.user.dto.auth.JwtAuthenticationResponse;
import pl.snz.pubweb.user.dto.auth.LoginRequest;
import pl.snz.pubweb.user.dto.user.CheckAuthResponse;
import pl.snz.pubweb.user.repo.UserRepository;
import pl.snz.pubweb.user.security.JwtTokenProvider;

import javax.validation.Valid;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signin")
    public JwtAuthenticationResponse jwtAuthenticationResponse(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getLoginOrEmail(),
                        loginRequest.getPassword()
                )
        );
        String jwt = jwtTokenProvider.generateToken(authentication);
        return new JwtAuthenticationResponse(jwt);

    }

    @GetMapping("/check")
    public CheckAuthResponse checkAuth(@RequestHeader("Authorization") String authorization) {
        return new CheckAuthResponse(true, 1l, Collections.singleton(authorization));
    }
}
