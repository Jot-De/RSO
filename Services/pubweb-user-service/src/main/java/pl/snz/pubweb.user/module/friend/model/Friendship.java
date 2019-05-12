package pl.snz.pubweb.user.module.friend.model;

import lombok.Data;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.user.module.user.model.User;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "friendship")
public class Friendship extends IdentifiableEntity<Long> {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2")
    private User user2;

    @Column(name = "since", nullable = false)
    private LocalDate since;

}
