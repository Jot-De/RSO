package pl.snz.pubweb.review.module.review.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Data
public class ReviewUpdateDto {

    @Length(min = 2, max = 4000)
    private String description;

    @Min(1) @Max(5)
    private int stars;
}
