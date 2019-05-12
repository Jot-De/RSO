package pl.snz.pubweb.review.module.history.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryEntryBrief {
    private Long id;
    private LocalDateTime modified;
}
