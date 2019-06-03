package pl.snz.pubweb.pub.module.suggest.dto;

import lombok.Builder;
import lombok.Getter;
import pl.snz.pubweb.pub.module.pub.dto.PubBriefDto;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;

import java.util.List;

@Getter
@Builder
public class PubSuggestion {

    private final PubBriefDto brief;
    private final List<TagDto> tagMatches;
}
