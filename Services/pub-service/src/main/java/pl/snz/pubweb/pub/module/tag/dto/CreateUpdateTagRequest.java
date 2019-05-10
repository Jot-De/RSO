package pl.snz.pubweb.pub.module.tag.dto;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Data
public class CreateUpdateTagRequest {
    @NotBlank @Length(min = 2, max = 30)
    private String name;

    @NotBlank @Length(min = 2, max = 100)
    private String description;
}
