package pl.snz.pubweb.user.module.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.user.module.permission_acceptance.dto.AcceptedPermission;

import java.net.URI;
import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserResponse {
    private Long id;
    private String login;
    private String email;
    private String displayName;
    private UDisplaySettings displaySettings;
    private UserPersonalInfoDto personalInformation;
    private List<AcceptedPermission> acceptedPermissions;
    private List<String> roles;
    private URI avatarUri;
    private boolean friend;
}
