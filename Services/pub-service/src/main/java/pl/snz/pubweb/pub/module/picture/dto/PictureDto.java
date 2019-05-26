package pl.snz.pubweb.pub.module.picture.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.net.URI;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PictureDto {
    private Long id;
    private String name;
    private String dataFormat;
    private String description;
}
