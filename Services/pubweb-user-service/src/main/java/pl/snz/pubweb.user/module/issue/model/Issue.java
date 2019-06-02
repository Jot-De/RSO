package pl.snz.pubweb.user.module.issue.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.user.module.user.model.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
@Entity
@Table(name = "issue")
public class Issue extends IdentifiableEntity<Long> {

    @Column(length = 4000, nullable = false)
    private String content;

    @Column(nullable = false)
    private LocalDateTime sent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Column
    private String solutionComment;

    @Column
    @Enumerated(EnumType.STRING)
    private IssueStatus status;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "issue", cascade = CascadeType.ALL)
    private Set<Comment> comments = new HashSet<>();
}
