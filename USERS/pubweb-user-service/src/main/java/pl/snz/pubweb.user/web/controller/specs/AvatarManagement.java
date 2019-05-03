package pl.snz.pubweb.user.web.controller.specs;

import org.springframework.http.ResponseEntity;
import pl.snz.pubweb.user.dto.common.Base64PictureDto;
import pl.snz.pubweb.user.dto.user.AddAvatarRequest;

public interface AvatarManagement {

    Base64PictureDto getAvatarForUser(Long userId);
    ResponseEntity<?> addAvatarForUser(Long userId, AddAvatarRequest addAvatarRequest);
    ResponseEntity<?> deleteAvatar(Long userId);
}
