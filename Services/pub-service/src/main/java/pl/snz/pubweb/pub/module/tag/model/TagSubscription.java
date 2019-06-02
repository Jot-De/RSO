package pl.snz.pubweb.pub.module.tag.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tag_subscription")
public class TagSubscription {

    @Id
    @Column
    private Long userId;

    @JoinColumn(name = "tag_id")
    @ManyToMany
    private List<Tag> subscriptions = new ArrayList<>();
}
