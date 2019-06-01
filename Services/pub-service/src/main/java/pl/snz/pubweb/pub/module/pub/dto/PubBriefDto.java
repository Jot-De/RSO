package pl.snz.pubweb.pub.module.pub.dto;

import lombok.*;
import pl.snz.pubweb.pub.module.common.dto.AddressDto;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PubBriefDto {
    Long id;
    String name;
    AddressDto address;
}
