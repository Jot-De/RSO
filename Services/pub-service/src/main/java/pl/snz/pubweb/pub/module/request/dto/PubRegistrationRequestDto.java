package pl.snz.pubweb.pub.module.request.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import pl.snz.pubweb.pub.module.common.dto.AddressDto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class PubRegistrationRequestDto {

    @NotNull
    private AddressDto address;

    @NotBlank @Length(min = 2, max = 100)
    private String name;

    @NotBlank @Length(min = 5, max = 4000)
    private String description;

}
