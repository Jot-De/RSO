package pl.snz.pubweb.user.dto.user;

import lombok.Data;

@Data
public class AddAvatarRequest {
    private String base64Avatar;
}
