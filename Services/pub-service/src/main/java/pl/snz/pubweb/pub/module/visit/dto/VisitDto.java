package pl.snz.pubweb.pub.module.visit.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class VisitDto extends VisitWishDto {
    private boolean isVisited;
    private LocalDateTime visited;
}
