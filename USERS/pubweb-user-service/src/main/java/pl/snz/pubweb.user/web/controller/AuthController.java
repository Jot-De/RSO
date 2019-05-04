package pl.snz.pubweb.user.web.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.user.security.UserPrincipal;
import pl.snz.pubweb.user.util.Mappers;
import pl.snz.pubweb.user.dto.auth.JwtAuthenticationResponse;
import pl.snz.pubweb.user.dto.auth.LoginRequest;
import pl.snz.pubweb.user.dto.user.CheckAuthResponse;
import pl.snz.pubweb.user.exception.NotFoundException;
import pl.snz.pubweb.user.model.Role;
import pl.snz.pubweb.user.model.User;
import pl.snz.pubweb.user.repo.UserRepository;
import pl.snz.pubweb.user.security.JwtTokenProvider;

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
