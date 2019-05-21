package pl.snz.pubweb.user.module.issue;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.user.module.issue.model.Issue;


public interface IssueRepository extends JpaRepository<Issue,Long>, JpaSpecificationExecutor<Issue> {

}
