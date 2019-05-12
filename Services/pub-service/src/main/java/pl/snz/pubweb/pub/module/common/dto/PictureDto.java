package pl.snz.pubweb.pub.module.common.dto;

import lombok.Builder;
import lombok.Data;

import java.net.URI;

@Data
@Builder
public class PictureDto {
    private Long id;
    private URI self;
    private String name;
    private String format;
    private String description;
}
