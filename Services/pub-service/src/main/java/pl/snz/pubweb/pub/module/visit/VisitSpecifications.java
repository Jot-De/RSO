package pl.snz.pubweb.pub.module.visit;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.data.JpaPredicates;
import pl.snz.pubweb.pub.module.pub.model.Pub_;
import pl.snz.pubweb.pub.module.visit.model.Visit;
import pl.snz.pubweb.pub.module.visit.model.VisitStatus;
import pl.snz.pubweb.pub.module.visit.model.Visit_;

import javax.persistence.criteria.Predicate;
import java.util.List;

@Service
public class VisitSpecifications {

    public Specification<Visit> visited(Long userId, Long pubId) {
        return withStatus(VisitStatus.VISITED, userId, pubId);
    }

    public Specification<Visit> wishes(Long userId, Long pubId) {
        return withStatus(VisitStatus.WANTS_TO_VISIT, userId, pubId);
    }

    public Specification<Visit> statuses(Long userId, List<Long> pubIds) {
        return (r,q,cb) ->  cb.and(cb.equal(r.get(Visit_.userId), userId), r.get(Visit_.pub).get(Pub_.id).in(pubIds));
    }

    private Specification<Visit> withStatus(VisitStatus status, Long userId, Long pubId) {
        return (r,q,cb) -> {
          Predicate result = JpaPredicates.truth(cb);
          result = userId == null ? result : cb.and(result, cb.equal(r.get(Visit_.userId), userId));
          result = pubId == null ? result : cb.and(result, cb.equal(r.get(Visit_.pub).get(Pub_.id), pubId));
          result = cb.and(result, cb.equal(r.get(Visit_.visitStatus), status));

          return result;
        };
    }
}
