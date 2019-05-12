package pl.snz.pubweb.user.module.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data @NoArgsConstructor
@Builder @AllArgsConstructor
public class UserPersonalInfoDto {
    @NotBlank
    private String firstName;
    @NotBlank
    private String surname;
    @NotBlank
    private String city;
    @NotBlank
    private String aboutMe;
}
