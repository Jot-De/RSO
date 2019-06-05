package pl.snz.pubweb.pub.module.pub;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.pub.module.picture.PictureMapper;
import pl.snz.pubweb.pub.module.picture.dto.PictureDtoWithData;
import pl.snz.pubweb.pub.module.pub.dto.PubDto;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.pub.model.Pub_;
import pl.snz.pubweb.pub.module.pub.presentation.PubMapper;
import pl.snz.pubweb.pub.module.tag.TagMapper;
import pl.snz.pubweb.pub.module.tag.TagRepository;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;
import pl.snz.pubweb.security.annotations.AdminApi;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pub")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PubController {

    private final PubRepository pubRepository;
    private final PubSearchSpecifications pubSearchSpecifications;
    private final PubMapper pubMapper;
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final PictureMapper pictureMapper;

    /**
     * Browse pubs
     */
    @GetMapping
    public Page<PubDto> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "20") int size,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String city,
                               @RequestParam(required = false) LocalDate addedAfter,
                               @RequestParam(required = false) LocalDate addedBefore,
                               @RequestParam(required = false) List<Long> tags) {
        Specification<Pub> searchSpec = pubSearchSpecifications.getSearchSpec(name, city, addedAfter, addedBefore, tags);
        Sort sort = Sort.by(Pub_.ADDED).descending().and(Sort.by(Pub_.ID).descending());

        return pubRepository.findAll(searchSpec, PageRequest.of(page, size, sort))
                .map(pubMapper::toGetResponse);
    }

    @GetMapping("{pubId}")
    public PubDto getOne(@PathVariable Long pubId) {
        final Pub pub = pubRepository.findOrThrow(pubId);

        return pubMapper.toGetResponse(pub);
    }

    @Transactional
    @GetMapping("{pubId}/picture/{pictureId}")
    public PictureDtoWithData getOnePicture(@PathVariable Long pubId, @PathVariable Long pictureId) {
        final Pub pub = pubRepository.findOrThrow(pubId);

        return pub.getPictures().stream()
                .filter(p -> p.getId().equals(pictureId))
                .map(pictureMapper::toData)
                .findFirst()
                .orElseThrow(NotFoundException.ofMessage("picture.not.found", "id", pictureId));

    }

    @Transactional
    @GetMapping("{pubId}/picture")
    public List<PictureDtoWithData> getAllPictures(@PathVariable Long pubId) {
        final Pub pub = pubRepository.findOrThrow(pubId);

        return Mappers.list(pub.getPictures(), pictureMapper::toData);

    }

    @DeleteMapping("{pubId}")
    @AdminApi
    public ResponseEntity delete(@PathVariable Long pubId) {
        pubRepository.findById(pubId).ifPresent(pubRepository::delete);

        return ResponseEntity.ok().build();
    }

    @PostMapping("{pubId}/tags")
    public List<TagDto> addTag(@PathVariable Long id, @RequestParam Long pubId) {
        final Pub pub = pubRepository.findById(id).orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", id));
        pub.getTags().add(tagRepository.findById(pubId).orElseThrow(NotFoundException.ofMessage("tag.not.found", "id", pubId)));

        return Just.of(pubRepository.save(pub)).map(Pub::getTags).map(l -> Mappers.list(l, tagMapper::toDto)).val();
    }

    @PutMapping("{pubId}/tags")
    public List<TagDto> setTags(@PathVariable Long pubId, @RequestParam List<Long> ids) {
        final Pub pub = pubRepository.findById(pubId).orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", pubId));
        pub.setTags(tagRepository.findAllById(ids).stream().collect(Collectors.toSet()));

        return Just.of(pub).map(pubRepository::save).map(Pub::getTags).map(l -> Mappers.list(l, tagMapper::toDto)).val();
    }

    @DeleteMapping("{pubId}/tags/{tagId}")
    public List<TagDto> deleteTag(@PathVariable Long id, @PathVariable Long pubId) {
        final Pub pub = pubRepository.findById(id).orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", id));
        pub.getTags().removeIf(t -> t.getId().equals(pubId));

        return Just.of(pub).map(pubRepository::save).map(Pub::getTags).map(l -> Mappers.list(l, tagMapper::toDto)).val();
    }

}
