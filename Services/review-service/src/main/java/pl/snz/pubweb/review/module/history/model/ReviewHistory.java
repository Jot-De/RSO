package pl.snz.pubweb.review.module.history.model;

import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.review.module.review.model.Review;

import javax.persistence.*;
import java.time.LocalDateTime;


@Table(name = "review_history")
@Entity
public class ReviewHistory extends IdentifiableEntity<Long> {


    @Column(length = 4000)
    private String oldText;

    @Column
    private int oldStars;

    @Column
    private LocalDateTime modified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;

    public String getOldText() {
        return oldText;
    }

    public void setOldText(String oldText) {
        this.oldText = oldText;
    }

    public int getOldStars() {
        return oldStars;
    }

    public void setOldStars(int oldStars) {
        this.oldStars = oldStars;
    }

    public LocalDateTime getModified() {
        return modified;
    }

    public void setModified(LocalDateTime modified) {
        this.modified = modified;
    }

    public Review getReview() {
        return review;
    }

    public void setReview(Review review) {
        this.review = review;
    }
}
