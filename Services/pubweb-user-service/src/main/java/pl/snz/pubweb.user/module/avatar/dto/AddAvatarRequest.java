package pl.snz.pubweb.user.module.avatar.dto;

import lombok.Data;

@Data
public class AddAvatarRequest {
    private String base64Avatar;
}
