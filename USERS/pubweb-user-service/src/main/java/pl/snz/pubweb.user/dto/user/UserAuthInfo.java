package pl.snz.pubweb.user.dto.user;

import lombok.Data;

import java.util.List;

@Data
public class UserAuthInfo {
    private Long userId;
    private List<String> roles;
}
