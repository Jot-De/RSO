package pl.snz.pubweb.user.dto.user;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data @AllArgsConstructor
public class CheckAuthResponse {
    private boolean valid;
    private Long userId;
    private Set<String> roles;
}
