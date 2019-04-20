package pl.snz.pubweb.user.service.user;

import lombok.NonNull;
import org.springframework.stereotype.Service;

/** Erases all user data except the display name and id which is then used to keep reviews consistent  */
@Service
public class UserSoftDeleteService {

    public void performSoftDelete(@NonNull Long id) {

    }
}
