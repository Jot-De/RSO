package pl.snz.pubweb.user.module.auth.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
    private Long userId;
    private Set<String> roles;

}
