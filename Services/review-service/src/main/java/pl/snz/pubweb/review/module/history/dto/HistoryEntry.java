package pl.snz.pubweb.review.module.history.dto;

import lombok.Data;

@Data
public class HistoryEntry extends HistoryEntryBrief {
    private String oldText;
    private int oldStars;
}
