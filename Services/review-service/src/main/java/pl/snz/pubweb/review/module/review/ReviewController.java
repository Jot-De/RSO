package pl.snz.pubweb.review.module.review;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.snz.pubweb.review.module.review.model.Review;

@RestController
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class ReviewController {

    @GetMapping
    public Page<Review> search(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                               @RequestParam(value = "size", required = false, defaultValue = "20") Integer size,
                               @RequestParam(value = "user", required = false) Long userId,
                               @RequestParam(value = "pub", required = false) Long pubId)  {

        return

    }
}
