package pl.snz.pubweb.pub.module.picture;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.snz.pubweb.pub.module.picture.model.Picture;

public interface PictureRepository extends JpaRepository<Picture, Long> {
}
