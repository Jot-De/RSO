package pl.snz.pubweb.user.module.issue;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.commons.data.JpaPredicates;
import pl.snz.pubweb.user.module.issue.model.Issue;
import pl.snz.pubweb.user.module.issue.model.IssueStatus;
import pl.snz.pubweb.user.module.issue.model.Issue_;
import pl.snz.pubweb.user.module.user.model.User_;

import javax.persistence.criteria.Predicate;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class IssueSpecifications {

    public Specification<Issue> search(@Nullable Long senderId,
                                       @Nullable LocalDateTime since,
                                       @Nullable List<IssueStatus> statuses) {
        return (r, q, cb) -> {
            Predicate result = JpaPredicates.truth(cb);
            result = senderId == null ? result : cb.and(result, cb.equal(r.get(Issue_.sender).get(User_.id), senderId));
            result = since == null ? result : cb.and(result, cb.greaterThanOrEqualTo(r.get(Issue_.sent), since));
            result = statuses == null || statuses.isEmpty() ? result : cb.and(result, r.get(Issue_.status).in(statuses));

            return result;
        };
    }
}
