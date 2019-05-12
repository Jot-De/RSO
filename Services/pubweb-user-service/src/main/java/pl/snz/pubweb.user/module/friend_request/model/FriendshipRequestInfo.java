package pl.snz.pubweb.user.module.friend_request.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data @Builder @AllArgsConstructor @NoArgsConstructor
public class FriendshipRequestInfo {
    private Long id;
    private Long requesterId;
    private Long targetId;
    private LocalDateTime received;
    private String message;
}
