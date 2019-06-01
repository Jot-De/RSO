package pl.snz.pubweb.pub.module.visit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.snz.pubweb.pub.module.pub.dto.PubBriefDto;

import java.time.LocalDate;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class VisitedPubDto {
    private Long id;
    private Long userId;
    private PubBriefDto pub;
    private LocalDate visited;
}
