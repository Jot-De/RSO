package pl.snz.pubweb.pub.module.picture;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.snz.pubweb.commons.dto.Base64PictureDto;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.common.data.Picture;

import java.util.Base64;

@RestController
@RequestMapping("/picture")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PictureController {

    private final PictureRepository pictureRepository;

    @GetMapping("{id}")
    public Base64PictureDto getOne(@PathVariable Long id) {
        final Picture picture = pictureRepository.findById(id)
                .orElseThrow(NotFoundException.ofMessage("picture.not.found", "id", id));
        return Base64PictureDto.builder()
                .id(picture.getId())
                .base64Picture(Base64.getMimeEncoder().encodeToString(picture.getBytes()))
                .build();
    }

}
