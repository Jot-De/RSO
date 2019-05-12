package pl.snz.pubweb.user.module.auth.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data @AllArgsConstructor
public class CheckAuthResponse {
    private boolean valid;
    private Long userId;
    private Set<String> roles;

    public static CheckAuthResponse invalid(){
        return new CheckAuthResponse(false, null, null);
    }
}
