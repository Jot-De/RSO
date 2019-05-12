package pl.snz.pubweb.review.module.review;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.review.module.review.dto.ReviewDto;
import pl.snz.pubweb.review.module.review.dto.ReviewUpdateDto;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReviewValidator {

    private final ReviewRepository repo;

    public void validateAddNew(ReviewDto dto) {
        if(repo.existsByUserIdAndPubIdAndAddedAfter(dto.getUserId(), dto.getPubId(), LocalDate.now().with(TemporalAdjusters.firstDayOfYear())))
            throw BadRequestException.general("review.already.exists");
    }

    public void validateUpdate(ReviewUpdateDto dto) {

    }

}
