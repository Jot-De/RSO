package pl.snz.pubweb.pub.module.pub.dto;

import lombok.Builder;
import lombok.Data;
import pl.snz.pubweb.pub.module.common.dto.AddressDto;
import pl.snz.pubweb.pub.module.picture.dto.PictureDto;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder(toBuilder = true)
public class PubDto {

    private Long id;
    private String name;
    private AddressDto address;
    private String description;
    private List<PictureDto> pictures;
    private List<TagDto> tags;
    private LocalDate added;

}
