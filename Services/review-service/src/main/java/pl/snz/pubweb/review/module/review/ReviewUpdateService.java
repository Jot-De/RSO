package pl.snz.pubweb.review.module.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.snz.pubweb.review.module.history.ReviewHistoryRepository;
import pl.snz.pubweb.review.module.history.model.ReviewHistory;
import pl.snz.pubweb.review.module.review.dto.ReviewUpdateDto;
import pl.snz.pubweb.review.module.review.model.Review;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReviewUpdateService {
    private final ReviewRepository reviewRepository;
    private final ReviewHistoryRepository reviewHistoryRepository;

    @Transactional
    public Review update(Review old, ReviewUpdateDto dto) {
        if(dto.getDescription().equals(old.getReviewText()) && dto.getStars() == old.getStars())
            return old;
        ReviewHistory history = new ReviewHistory();
        history.setModified(LocalDateTime.now());
        history.setOldText(old.getReviewText());
        history.setOldStars(old.getStars());
        history.setReview(old);
        reviewHistoryRepository.save(history);

        old.getReviewHistory().add(history);
        old.setStars(dto.getStars());
        old.setReviewText(dto.getDescription());
        return reviewRepository.save(old);
    }
}
