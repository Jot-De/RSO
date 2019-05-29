package pl.snz.pubweb.review.module.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @Builder
@NoArgsConstructor @AllArgsConstructor
public class GetPubAverageRatingResponse {

    private Double average;
    private Long pubId;
    private Long ratingCount;
}
