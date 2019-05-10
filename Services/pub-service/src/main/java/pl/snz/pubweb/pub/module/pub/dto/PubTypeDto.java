package pl.snz.pubweb.pub.module.pub.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PubTypeDto {

    private String name;
    private String description;
}
