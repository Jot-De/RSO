package pl.snz.pubweb.user.dto.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private String displayName;
}
