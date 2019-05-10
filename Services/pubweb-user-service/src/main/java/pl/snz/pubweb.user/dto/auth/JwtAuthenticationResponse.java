package pl.snz.pubweb.user.dto.auth;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtAuthenticationResponse {
    private String accessToken;
    private final String tokenType = "Bearer";
    private Long userId;

}
