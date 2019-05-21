package pl.snz.pubweb.pub.module.pub;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.pub.module.pub.dto.PubDto;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.pub.presentation.PubMapper;
import pl.snz.pubweb.pub.module.tag.TagMapper;
import pl.snz.pubweb.pub.module.tag.TagRepository;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;
import pl.snz.pubweb.security.annotations.AdminApi;

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

    /** Browse pubs */
    @GetMapping
    public Page<PubDto> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "20") int size,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String city,
                               @RequestParam(required = false) List<Long> tags) {
            Specification<Pub> searchSpec = pubSearchSpecifications.getSearchSpec(name, city, tags);

            return pubRepository.findAll(searchSpec, PageRequest.of(page, size)).map(pubMapper::toGetResponse);
    }

    @GetMapping("{id}")
    public PubDto getOne(@PathVariable  Long id) {
        return pubRepository.findById(id).map(pubMapper::toGetResponse)
                .orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", id));
    }


    @DeleteMapping("{id}")
    @AdminApi
    public ResponseEntity delete(@PathVariable Long id) {
        pubRepository.findById(id).ifPresent(pubRepository::delete);

        return ResponseEntity.ok().build();
    }

    @PostMapping("{id}/tags")
    public List<TagDto> addTag(@PathVariable Long id, @RequestParam Long tagId) {
        final Pub pub = pubRepository.findById(id).orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", id));
        pub.getTags().add(tagRepository.findById(tagId).orElseThrow(NotFoundException.ofMessage("tag.not.found", "id", tagId)));

        return Just.of(pubRepository.save(pub)).map(Pub::getTags).map(l -> Mappers.list(l, tagMapper::toDto)).val();
    }

    @PutMapping("{id}/tags")
    public List<TagDto> setTags(@PathVariable Long id, @RequestParam List<Long> ids) {
        final Pub pub = pubRepository.findById(id).orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", id));
        pub.setTags(tagRepository.findAllById(ids).stream().collect(Collectors.toSet()));

        return Just.of(pub).map(pubRepository::save).map(Pub::getTags).map(l -> Mappers.list(l, tagMapper::toDto)).val();
    }

    @DeleteMapping("{id}/tags/{tagId}")
    public List<TagDto> deleteTag(@PathVariable Long id, @PathVariable Long tagId) {
        final Pub pub = pubRepository.findById(id).orElseThrow(NotFoundException.ofMessage("pub.not.found", "id", id));
        pub.getTags().removeIf(t -> t.getId().equals(tagId));

        return Just.of(pub).map(pubRepository::save).map(Pub::getTags).map(l -> Mappers.list(l, tagMapper::toDto)).val();
    }









}
