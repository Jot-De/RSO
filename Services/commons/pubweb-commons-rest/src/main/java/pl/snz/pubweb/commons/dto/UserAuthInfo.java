package pl.snz.pubweb.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor @AllArgsConstructor
public class UserAuthInfo {
    private Long userId;
    private String login;
    private List<String> roles;
    private List<String> permissions;
}
