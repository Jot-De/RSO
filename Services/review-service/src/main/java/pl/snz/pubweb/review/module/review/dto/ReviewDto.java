package pl.snz.pubweb.review.module.review.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import pl.snz.pubweb.review.module.history.dto.HistoryEntryBrief;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class ReviewDto {

    private Long id;

    @NotNull
    private Long userId;

    @NotNull
    private Long pubId;

    @Length(min = 2, max = 4000)
    private String description;

    @Min(1) @Max(5)
    private int stars;

    private List<HistoryEntryBrief> history;

}
