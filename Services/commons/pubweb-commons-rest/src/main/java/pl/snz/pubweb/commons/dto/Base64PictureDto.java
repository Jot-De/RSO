package pl.snz.pubweb.commons.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Base64PictureDto {
    private Long id;
    private String base64Picture;
    private String dataFormat;
}
