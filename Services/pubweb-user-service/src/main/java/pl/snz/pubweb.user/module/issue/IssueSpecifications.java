package pl.snz.pubweb.user.module.issue;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.commons.data.JpaPredicates;
import pl.snz.pubweb.user.module.issue.model.Issue;
import pl.snz.pubweb.user.module.issue.model.IssueStatus;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;

@Component
public class IssueSpecifications {

    public Specification<Issue> search(@Nullable Long senderId,
                                       @Nullable LocalDateTime since,
                                       @Nullable IssueStatus status) {
        return (r,q,cb) -> {
          Predicate result = JpaPredicates.truth(cb);
//          result = senderId == null ? result : cb.and(result, cb.equal(Issue_.))
            return result;
        };
    }
}
