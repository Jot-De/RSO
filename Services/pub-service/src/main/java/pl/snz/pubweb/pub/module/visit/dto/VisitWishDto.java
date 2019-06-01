package pl.snz.pubweb.pub.module.visit.dto;

import lombok.*;
import pl.snz.pubweb.pub.module.pub.dto.PubBriefDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VisitWishDto {
    private Long id;
    private Long userId;
    private PubBriefDto pub;
}
