package pl.snz.pubweb.user.module.avatar;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.user.module.user.model.User;

import javax.persistence.*;

@Entity
@Table(name = "avatar")
@Data @NoArgsConstructor @Builder @AllArgsConstructor
public class Avatar extends IdentifiableEntity<Long> {
j
    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length = 100000)
    private byte[] bytes;

    @Column(length = 60)
    private String dataFormat;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

}
