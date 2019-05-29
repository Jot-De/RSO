package pl.snz.pubweb.review.module.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AveragePubRating {
    private double average;
    private Long pubId;
    private Long ratingCount;
}
