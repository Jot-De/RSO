package pl.snz.pubweb.user.module.issue.model;


import lombok.Getter;
import lombok.Setter;
import pl.snz.pubweb.commons.data.IdentifiableEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter @Setter
@Entity(name = "comment")
public class Comment extends IdentifiableEntity<Long> {

    @JoinColumn(name = "issue_id")
    @ManyToOne
    private Issue issue;

    @Column
    private Long authorId;

    @Column(length = 500)
    private String content;

    @Column
    private LocalDateTime createDate;


}
