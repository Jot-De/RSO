package pl.snz.pubweb.pub.module.request.dto;

import lombok.Builder;
import lombok.Data;
import pl.snz.pubweb.pub.module.common.dto.AddressDto;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;

import java.time.LocalDate;

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
}
