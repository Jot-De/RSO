package pl.snz.pubweb.user.module.friend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class FriendshipInfo {
    private Long user;
    private Long friend;
    private LocalDate since;
}
