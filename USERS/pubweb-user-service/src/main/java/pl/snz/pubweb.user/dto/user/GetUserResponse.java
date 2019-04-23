package pl.snz.pubweb.user.dto.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data @NoArgsConstructor @AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetUserResponse {
    private String login;
    private String email;
    private String displayName;
    private String aboutMe;
    private UDisplaySettings displaySettings;
    private UserPersonalInfoDto personalInformation;
    private List<AcceptedPermission> acceptedPermissions;
    private List<String> roles;
}
