package pl.snz.pubweb.review.module.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.review.module.history.ReviewHistoryMapper;
import pl.snz.pubweb.review.module.review.dto.GetPubAverageRatingResponse;
import pl.snz.pubweb.review.module.review.dto.ReviewDto;
import pl.snz.pubweb.review.module.review.model.AveragePubRating;
import pl.snz.pubweb.review.module.review.model.Review;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReviewMapper {

    private final ReviewHistoryMapper historyMapper;

    public ReviewDto toDto(Review review) {
        return ReviewDto.builder()
                .id(review.getId())
                .description(review.getReviewText())
                .userId(review.getUserId())
                .pubId(review.getPubId())
                .stars(review.getStars())
                .history(Mappers.list(review.getReviewHistory(), historyMapper::toBrief))
                .added(review.getAdded())
                .build();
    }

    public Review toEntity(ReviewDto dto) {
        Review review = new Review();
        review.setStars(dto.getStars());
        review.setUserId(dto.getUserId());
        review.setAdded(LocalDate.now());
        review.setPubId(dto.getPubId());
        review.setReviewText(dto.getDescription());
        return review;
    }

    public GetPubAverageRatingResponse toAverageResponse(AveragePubRating rating) {

        return GetPubAverageRatingResponse.builder()
                .average(rating.getAverage())
                .pubId(rating.getPubId())
                .ratingCount(rating.getRatingCount())
                .build();
    }

}
