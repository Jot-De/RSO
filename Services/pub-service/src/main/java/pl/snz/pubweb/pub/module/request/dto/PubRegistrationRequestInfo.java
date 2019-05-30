package pl.snz.pubweb.pub.module.request.dto;

import lombok.Builder;
import lombok.Data;
import pl.snz.pubweb.pub.module.common.dto.AddressDto;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class PubRegistrationRequestInfo {
    private Long id;
    private String name;
    private AddressDto address;
    private PubRegistrationStatus status;
    private String description;
    private LocalDate added;
    private Long requesterId;
    private Long pubId;
    private List<TagDto> tags;
}
