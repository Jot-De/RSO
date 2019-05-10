package pl.snz.pubweb.user.dto.user.friend;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class SendFriendshipRequest {
    @NotNull
    private Long targetUser;

    @NotBlank
    private String message;
}
