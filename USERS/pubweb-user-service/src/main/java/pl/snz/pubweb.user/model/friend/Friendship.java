package pl.snz.pubweb.user.model.friend;
import lombok.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import pl.snz.pubweb.user.model.IdentifiableEntity;
import pl.snz.pubweb.user.model.Timestamp;
import pl.snz.pubweb.user.model.User;

import javax.persistence.*;
import java.io.Serializable;
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

    @Column(name = "received")
    private LocalDate since;

}
