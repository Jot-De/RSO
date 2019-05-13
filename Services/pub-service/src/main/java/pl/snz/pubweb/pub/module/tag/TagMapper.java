package pl.snz.pubweb.pub.module.tag;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;
import pl.snz.pubweb.pub.module.tag.model.Tag;

@Service
public class TagMapper {

    public TagDto toDto(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .description(tag.getDescription())
                .name(tag.getName())
                .build();
    }

}
