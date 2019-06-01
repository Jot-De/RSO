package pl.snz.pubweb.pub.module.visit;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.pub.module.pub.presentation.PubMapper;
import pl.snz.pubweb.pub.module.visit.dto.VisitWishDto;
import pl.snz.pubweb.pub.module.visit.dto.VisitedPubDto;
import pl.snz.pubweb.pub.module.visit.model.Visit;

@Service
@RequiredArgsConstructor
public class VisitMapper {

    private final PubMapper pubMapper;

    public VisitedPubDto toVisitedDto(Visit visit) {
        return VisitedPubDto.builder()
                .pub(pubMapper.toBrief(visit.getPub()))
                .userId(visit.getUserId())
                .build();
    }

    public VisitWishDto toWishDto(Visit visit) {
        return VisitWishDto.builder()
                .pub(pubMapper.toBrief(visit.getPub()))
                .userId(visit.getUserId())
                .build();
    }
}
