package pl.snz.pubweb.pub.module.picture;

import org.springframework.stereotype.Service;
import pl.snz.pubweb.pub.module.picture.dto.PictureDto;
import pl.snz.pubweb.pub.module.picture.dto.PictureDtoWithData;
import pl.snz.pubweb.pub.module.picture.model.Picture;

import java.util.Base64;

@Service
public class PictureMapper {

    private final Base64.Encoder encoder = Base64.getMimeEncoder();
    private final Base64.Decoder decoder = Base64.getMimeDecoder();

    public PictureDto toInfo(Picture picture) {
        return PictureDto.builder()
                .id(picture.getId())
                .dataFormat(picture.getFormat())
                .name(picture.getName())
                .description(picture.getDescription())
                .build();
    }

    public PictureDtoWithData toData(Picture picture) {
        PictureDtoWithData pictureDtoWithData = new PictureDtoWithData();
        pictureDtoWithData.setId(picture.getId());
        pictureDtoWithData.setDataFormat(picture.getFormat());
        pictureDtoWithData.setName(picture.getName());
        pictureDtoWithData.setDescription(picture.getDescription());
        pictureDtoWithData.setBase64Picture(encoder.encodeToString(picture.getBytes()));
        return pictureDtoWithData;
    }

    public Picture toEntity(PictureDtoWithData dto) {
        return Picture.builder()
                .bytes(decoder.decode(dto.getBase64Picture()))
                .description(dto.getDescription())
                .format(dto.getDataFormat())
                .name(dto.getName())
                .build();

    }
}
