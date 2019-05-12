package pl.snz.pubweb.review.module.review;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.review.module.review.dto.ReviewDto;
import pl.snz.pubweb.review.module.review.dto.ReviewUpdateDto;
import pl.snz.pubweb.review.module.review.model.Review;
import pl.snz.pubweb.security.SecurityService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReviewController {

    private final ReviewRepository repo;
    private final ReviewMapper mapper;
    private final ReviewValidator reviewValidator;
    private final ReviewUpdateService reviewUpdateService;
    private final SecurityService securityService;

    @GetMapping
    public Page<ReviewDto> search(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                  @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                                  @RequestParam(value = "user", required = false) Long userId,
                                  @RequestParam(value = "pub", required = false) Long pubId)  {
        return repo.search(userId, pubId, PageRequest.of(page, size)).map(mapper::toDto);
    }

    @PostMapping
    public ReviewDto add(@RequestBody @Valid ReviewDto dto) {
        reviewValidator.validateAddNew(dto);
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
        securityService.requireSelf(review.getUserId());
        repo.delete(review);
        return ResponseEntity.ok().build();
    }


}
