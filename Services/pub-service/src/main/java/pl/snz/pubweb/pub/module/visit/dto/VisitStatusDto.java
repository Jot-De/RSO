package pl.snz.pubweb.pub.module.visit.dto;

import lombok.Builder;
import lombok.Data;
import pl.snz.pubweb.pub.module.visit.model.VisitStatus;

@Data
@Builder
public class VisitStatusDto {
    private VisitStatus visitStatus;
    private Long visitId;
    private Long pubId;
    private Long userId;
}
