package pl.snz.pubweb.pub.module.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AddressDto {

    private String city;
    private String street;
    private String number;

}
