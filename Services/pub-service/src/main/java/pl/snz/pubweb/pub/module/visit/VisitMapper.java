package pl.snz.pubweb.pub.module.visit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.pub.module.pub.presentation.PubMapper;
import pl.snz.pubweb.pub.module.visit.dto.VisitDto;
import pl.snz.pubweb.pub.module.visit.dto.VisitStatusDto;
import pl.snz.pubweb.pub.module.visit.dto.VisitWishDto;
import pl.snz.pubweb.pub.module.visit.dto.VisitedPubDto;
import pl.snz.pubweb.pub.module.visit.model.Visit;
import pl.snz.pubweb.pub.module.visit.model.VisitStatus;

@Service
@RequiredArgsConstructor
public class VisitMapper {

    private final PubMapper pubMapper;

    public VisitedPubDto toVisitedDto(Visit visit) {
        return VisitedPubDto.builder()
                .pub(pubMapper.toBrief(visit.getPub()))
                .userId(visit.getUserId())
                .visited(visit.getVisited())
                .build();
    }

    public VisitWishDto toWishDto(Visit visit) {
        return VisitWishDto.builder()
                .pub(pubMapper.toBrief(visit.getPub()))
                .userId(visit.getUserId())
                .build();
    }

    public VisitDto toVisitDto(Visit visit) {
        VisitDto dto = new VisitDto();
        dto.setVisited(visit.getVisitStatus() == VisitStatus.VISITED);
        dto.setId(visit.getId());
        dto.setPub(pubMapper.toBrief(visit.getPub()));
        dto.setUserId(visit.getUserId());
        return dto;
    }

    public VisitStatusDto toStatusDto(Visit visit) {
        return VisitStatusDto.builder()
                .visitId(visit.getId())
                .pubId(visit.getPub().getId())
                .userId(visit.getUserId())
                .visitStatus(visit.getVisitStatus())
                .build();
    }
}
