package pl.snz.pubweb.test.review.review;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.jdbc.Sql;
import pl.snz.pubweb.review.module.review.ReviewRepository;
import pl.snz.pubweb.review.module.review.model.AveragePubRating;
import pl.snz.pubweb.review.module.review.model.Review;
import pl.snz.pubweb.review.module.review.model.Review_;
import pl.snz.pubweb.test.review.ReviewServiceIntegrationTest;

import java.time.LocalDate;

public class ReviewRepoTest extends ReviewServiceIntegrationTest {

    @Autowired
    private ReviewRepository repo;

    @Test
    @Sql(scripts = "/sql/average-rating-repo-test.sql")
    public void testAverageRating() {
        Assert.assertNotNull(repo);
        final AveragePubRating averageRatingForPub = repo.getAverageRatingForPub(1l);
        Assert.assertEquals(3.5, averageRatingForPub.getAverage(), 0.01);
        final Page<AveragePubRating> averageRatings = repo.getTopAverageRatings(PageRequest.of(0, 100));
        Assert.assertEquals(averageRatings.getNumberOfElements(),2);
        Assert.assertEquals(averageRatings.getContent().get(0).getAverage(), 3.5, 0.01);
        Assert.assertEquals(averageRatings.getContent().get(1).getAverage(), 2, 0.01);
    }


    @Test
    @Sql(scripts = "/sql/review-search.sql")
    public void testSearch() {
        final Page<Review> result = repo.search(null, null, LocalDate.of(2019, 05, 15), LocalDate.of(2019, 05, 20), PageRequest.of(0, 20));
        Assert.assertEquals(4, result.getNumberOfElements());

        final Page<Review> result1 = repo.search(1l, null, null, null, PageRequest.of(0, 20));
        Assert.assertEquals(3, result1.getNumberOfElements());

        final Page<Review> result2 = repo.search(null, 1l, null, null, PageRequest.of(0, 20));
        Assert.assertEquals(2, result2.getNumberOfElements());
    }
}
