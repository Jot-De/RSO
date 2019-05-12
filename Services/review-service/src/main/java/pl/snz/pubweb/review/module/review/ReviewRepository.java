package pl.snz.pubweb.review.module.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.commons.data.JpaPredicates;
import pl.snz.pubweb.review.module.review.model.Review;
import pl.snz.pubweb.review.module.review.model.Review_;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    default Page<Review> search(Long userId, Long pubId, Pageable p) {
        Specification<Review> spec = (r,q,cb) -> {
            Predicate result = JpaPredicates.truth(cb);
            result = userId == null ? result : cb.and(result, cb.equal(r.get(Review_.userId), userId));
            result = pubId == null ? result : cb.and(result, cb.equal(r.get(Review_.pubId), pubId));
            return result;
        };
        return this.findAll(spec,p);
    }

    boolean existsByUserIdAndPubIdAndAddedAfter(Long userId, Long pubId, LocalDate addedAfter);
}
