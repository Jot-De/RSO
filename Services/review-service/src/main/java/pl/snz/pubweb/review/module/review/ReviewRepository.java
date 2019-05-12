package pl.snz.pubweb.review.module.review;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.review.module.review.model.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, JpaSpecificationExecutor<Review> {

    default Page<Review> search(Long userId, Long pubId) {
        return
    }
}
