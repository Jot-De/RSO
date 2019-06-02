package pl.snz.pubweb.user.module.issue;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.user.module.issue.model.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    Page<Comment> findByIssueId(Long issueId, Pageable p);
}
