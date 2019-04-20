package pl.snz.pubweb.user.model.friend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.user.model.IdentifiableEntity;
import pl.snz.pubweb.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data @NoArgsConstructor @AllArgsConstructor
@Table(name = "friend_request")
@Entity
public class FriendshipRequest extends IdentifiableEntity<Long> {

    @JoinColumn(name = "requester_id")
    @ManyToOne
    private User requester;

    @JoinColumn(name = "target_id")
    @ManyToOne
    private User target;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private FriendRequestStatus friendRequestStatus;

    @Column(name = "receive_date")
    private LocalDateTime received;

    @Column(name = "message")
    private String message;
}
