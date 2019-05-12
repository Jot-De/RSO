package pl.snz.pubweb.commons.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor @NoArgsConstructor
public class Base64PictureDto {
    private Long id;
    private String base64Picture;
    private String dataFormat;
}
