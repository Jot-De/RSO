package pl.snz.pubweb.review.module.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pl.snz.pubweb.commons.data.JpaPredicates;
import pl.snz.pubweb.review.module.review.model.AveragePubRating;
import pl.snz.pubweb.review.module.review.model.Review;
import pl.snz.pubweb.review.module.review.model.Review_;

import javax.persistence.criteria.Predicate;
import java.time.LocalDate;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    default Page<Review> search(Long userId, Long pubId, LocalDate from, LocalDate to, Pageable p) {
        Specification<Review> spec = (r,q,cb) -> {
            Predicate result = JpaPredicates.truth(cb);
            result = userId == null ? result : cb.and(result, cb.equal(r.get(Review_.userId), userId));
            result = pubId == null ? result : cb.and(result, cb.equal(r.get(Review_.pubId), pubId));
            result = from == null ? result : cb.and(result, cb.greaterThanOrEqualTo(r.get(Review_.added), from));
            result = to == null ? result : cb.and(result, cb.lessThan(r.get(Review_.added), to.plusDays(1))); ///TODO strange workaround for jpa error
            return result;
        };
        return this.findAll(spec,p);
    }

    boolean existsByUserIdAndPubIdAndAddedAfter(Long userId, Long pubId, LocalDate addedAfter);

    @Query("SELECT new pl.snz.pubweb.review.module.review.model.AveragePubRating(AVG(stars) as average, r.pubId as pubId, count(pubId) as ratingCount) from Review r where pubId =:pubId")
    AveragePubRating getAverageRatingForPub(@Param("pubId") Long pubId);

    @Query("SELECT new pl.snz.pubweb.review.module.review.model.AveragePubRating(AVG(stars) as average, r.pubId as pubId, count(pubId) as ratingCount)" +
            " FROM Review r" +
            " GROUP BY pubId" +
            " ORDER BY AVG(stars) desc"
    )
    Page<AveragePubRating> getTopAverageRatings(Pageable p);

}
