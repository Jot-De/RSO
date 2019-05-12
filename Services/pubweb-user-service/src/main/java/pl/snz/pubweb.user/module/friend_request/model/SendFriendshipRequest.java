package pl.snz.pubweb.user.module.friend_request.model;

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
