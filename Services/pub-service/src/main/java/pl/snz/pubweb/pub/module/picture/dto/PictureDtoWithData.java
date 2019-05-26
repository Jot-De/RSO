package pl.snz.pubweb.pub.module.picture.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PictureDtoWithData extends PictureDto {

    private String base64Picture;

}