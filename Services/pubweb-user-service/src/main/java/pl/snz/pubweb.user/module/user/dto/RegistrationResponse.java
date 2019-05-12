package pl.snz.pubweb.user.module.user.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RegistrationResponse {
    private String displayName;
}
