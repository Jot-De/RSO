package pl.snz.pubweb.review.module.history.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class HistoryEntry extends HistoryEntryBrief {
    private String oldText;
    private int oldStars;
    private LocalDateTime check = LocalDateTime.now();
}
