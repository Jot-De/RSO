package pl.snz.pubweb.user.dto.common;

import lombok.Data;

@Data
public class Base64PictureDto {
    private Long id;
    private String base64Picture;
}
