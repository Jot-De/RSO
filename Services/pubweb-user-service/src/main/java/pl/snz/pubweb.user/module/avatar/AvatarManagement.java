package pl.snz.pubweb.user.module.avatar;

import org.springframework.http.ResponseEntity;
import pl.snz.pubweb.commons.dto.Base64PictureDto;

public interface AvatarManagement {

    Base64PictureDto getAvatarForUser(Long userId);
    ResponseEntity<?> addAvatarForUser(Long userId, Base64PictureDto addAvatarRequest);
    ResponseEntity<?> deleteAvatar(Long userId);
}
