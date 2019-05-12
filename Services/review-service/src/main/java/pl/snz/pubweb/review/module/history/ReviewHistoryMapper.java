package pl.snz.pubweb.review.module.history;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.review.module.history.dto.HistoryEntry;
import pl.snz.pubweb.review.module.history.dto.HistoryEntryBrief;
import pl.snz.pubweb.review.module.history.model.ReviewHistory;

@Service
public class ReviewHistoryMapper {

    public HistoryEntryBrief toBrief(ReviewHistory history) {
        return withBaseFields(new HistoryEntryBrief(), history);
    }

    public HistoryEntry toEntry(ReviewHistory history) {
        HistoryEntry entry = withBaseFields(new HistoryEntry(), history);
        entry.setOldStars(history.getOldStars());
        entry.setOldText(history.getOldText());
        return entry;
    }

    private <T extends HistoryEntryBrief> T withBaseFields(T newT, ReviewHistory history) {
        newT.setId(history.getId());
        newT.setModified(history.getModified());
        return newT;
    }
}
