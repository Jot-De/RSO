package pl.snz.pubweb.review.module.review;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.review.module.history.ReviewHistoryMapper;
import pl.snz.pubweb.review.module.history.dto.HistoryEntry;
import pl.snz.pubweb.review.module.review.dto.GetPubAverageRatingResponse;
import pl.snz.pubweb.review.module.review.dto.ReviewDto;
import pl.snz.pubweb.review.module.review.dto.ReviewUpdateDto;
import pl.snz.pubweb.review.module.review.model.Review;
import pl.snz.pubweb.review.module.review.model.Review_;
import pl.snz.pubweb.security.SecurityService;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("review")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReviewController {

    private final ReviewRepository repo;
    private final ReviewMapper mapper;
    private final ReviewHistoryMapper historyMapper;
    private final ReviewValidator reviewValidator;
    private final ReviewUpdateService reviewUpdateService;
    private final SecurityService securityService;

    @GetMapping
    public Page<ReviewDto> search(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                  @RequestParam(value = "user", required = false) Long userId,
                                  @RequestParam(value = "pub", required = false) Long pubId,
                                  @RequestParam(value = "dateFrom", required = false) LocalDate dateFrom,
                                  @RequestParam(value = "dateTo", required = false) LocalDate dateTo) {
        return repo.search(userId, pubId, dateFrom, dateTo, PageRequest.of(page, size, Sort.by(Review_.ADDED).descending())).map(mapper::toDto);
    }

    @GetMapping("top")
    public Page<GetPubAverageRatingResponse> getTop(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                    @RequestParam(value = "size", required = false, defaultValue = "20") Integer size) {
        return repo.getTopAverageRatings(PageRequest.of(page, size)).map(mapper::toAverageResponse);
    }

    @GetMapping(path = "average")
    public GetPubAverageRatingResponse getAverageForPub(@RequestParam(value = "pub") Long pubId) {
        return Optional.ofNullable(repo.getAverageRatingForPub(pubId)).map(mapper::toAverageResponse).orElseGet(() -> GetPubAverageRatingResponse.noResult(pubId));
    }

    @GetMapping("{id}")
    public ReviewDto getOne(@PathVariable Long id) {
        final Review review = repo.findById(id).orElseThrow(NotFoundException.ofMessage("review.not.found", "id", id));
        return Just.of(review).map(mapper::toDto).val();
    }

    @PostMapping
    public ReviewDto add(@RequestBody @Valid ReviewDto dto) {
        reviewValidator.validateAddNew(dto);
        securityService.requireSelf(dto.getUserId());
        return Just.of(dto).map(mapper::toEntity).map(repo::save).map(mapper::toDto).val();
    }

    @PutMapping("{id}")
    public ReviewDto update(@RequestBody @Valid ReviewUpdateDto dto, @PathVariable Long id) {
        final Review review = repo.findById(id).orElseThrow(NotFoundException.ofMessage("review.not.found", "id", id));
        securityService.requireSelf(review.getUserId());
        return Just.of(review).map(r -> reviewUpdateService.update(r, dto)).map(mapper::toDto).val();
    }

    @DeleteMapping
    public ResponseEntity delete(@PathVariable Long id) {
        final Review review = repo.findById(id).orElseThrow(NotFoundException.ofMessage("review.not.found", "id", id));
        securityService.requireSelfOrAdmin(review.getUserId());
        repo.delete(review);
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/history")
    public List<HistoryEntry> getHistory(@PathVariable Long id) {
        final Review review = repo.findById(id).orElseThrow(NotFoundException.ofMessage("review.not.found", "id", id));
        return Just.of(review)
                .map(Review::getReviewHistory)
                .map(s -> Mappers.list(s, historyMapper::toEntry))
                .val();
    }

}
