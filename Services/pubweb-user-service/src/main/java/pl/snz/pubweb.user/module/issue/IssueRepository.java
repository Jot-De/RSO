package pl.snz.pubweb.user.module.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.user.module.issue.model.Issue;


public interface IssueRepository extends JpaRepository<Issue,Long>, JpaSpecificationExecutor<Issue> {
    default Issue findOrThrow(Long id) {
        return this.findById(id).orElseThrow(NotFoundException.ofMessage("issue.not.found", "id", id));
    }
}
