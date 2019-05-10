package pl.snz.pubweb.security.subject;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class UserAuthInfo {
    private Long userId;
    private String login;
    private List<String> roles;
    private List<String> permissions;
}
