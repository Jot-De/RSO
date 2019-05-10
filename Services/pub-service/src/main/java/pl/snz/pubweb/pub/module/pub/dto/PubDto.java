package pl.snz.pubweb.pub.module.pub.dto;

import lombok.Builder;
import lombok.Data;
import pl.snz.pubweb.pub.module.common.dto.AddressDto;
import pl.snz.pubweb.pub.module.common.dto.PictureDto;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class PubDto {

    private Long id;
    private String name;
    private AddressDto address;
    private List<PictureDto> pictures;
    private List<TagDto> tags;
    private LocalDate added;

}
