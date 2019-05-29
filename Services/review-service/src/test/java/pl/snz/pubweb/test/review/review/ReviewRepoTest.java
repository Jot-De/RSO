package pl.snz.pubweb.test.review.review;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import pl.snz.pubweb.review.module.review.ReviewRepository;
import pl.snz.pubweb.review.module.review.model.AveragePubRating;
import pl.snz.pubweb.test.review.ReviewServiceIntegrationTest;

public class ReviewRepoTest extends ReviewServiceIntegrationTest {

    @Autowired
    private ReviewRepository repo;

    @Test
    public void test() {
        Assert.assertNotNull(repo);
        final AveragePubRating averageRatingForPub = repo.getAverageRatingForPub(1l);
        Assert.assertEquals(averageRatingForPub.getAverage(), 3.5, 0.01);
        final Page<AveragePubRating> averageRatings = repo.getTopAverageRatings(PageRequest.of(0, 100));
        Assert.assertEquals(averageRatings.getNumberOfElements(),2);
    }
}
