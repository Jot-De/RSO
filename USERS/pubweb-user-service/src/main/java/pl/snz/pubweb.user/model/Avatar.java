package pl.snz.pubweb.user.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "avatar")
@Data
public class Avatar extends IdentifiableEntity<Long> {

    @Lob @Basic(fetch = FetchType.LAZY)
    @Column(length = 100000)
    private byte[] bytes;

    @JoinColumn(name = "user_id")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User user;

}
