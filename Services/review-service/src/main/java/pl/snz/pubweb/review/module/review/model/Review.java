package pl.snz.pubweb.review.module.review.model;

import pl.snz.pubweb.commons.data.IdentifiableEntity;
import pl.snz.pubweb.review.module.history.model.ReviewHistory;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "review")
public class Review extends IdentifiableEntity<Long> {

    @Column
    private Long userId;

    @Column
    private Long pubId;

    @Column
    private String reviewText;

    @Column
    private int stars;

    @Column
    private LocalDate added;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "review")
    private Set<ReviewHistory> reviewHistory = new HashSet<>();

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPubId() {
        return pubId;
    }

    public void setPubId(Long pubId) {
        this.pubId = pubId;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }

    public int getStars() {
        return stars;
    }

    public void setStars(int stars) {
        this.stars = stars;
    }

    public LocalDate getAdded() {
        return added;
    }

    public void setAdded(LocalDate added) {
        this.added = added;
    }

    public Set<ReviewHistory> getReviewHistory() {
        return reviewHistory;
    }

    public void setReviewHistory(Set<ReviewHistory> reviewHistory) {
        this.reviewHistory = reviewHistory;
    }
}
