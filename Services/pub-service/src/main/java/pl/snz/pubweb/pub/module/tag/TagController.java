package pl.snz.pubweb.pub.module.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;
import pl.snz.pubweb.pub.module.tag.dto.CreateUpdateTagRequest;
import pl.snz.pubweb.pub.module.tag.model.Tag;
import pl.snz.pubweb.security.annotations.AdminApi;

import java.util.Optional;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagController {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagSearchSpecs tagSearchSpecs;

    @GetMapping
    public Page<TagDto> getAll(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "50") int size, @RequestParam(required = false) String nameLike) {
        return tagRepository.findAll(tagSearchSpecs.searchSpec(nameLike),
                PageRequest.of(page, size))
                .map(tagMapper::toDto);
    }

    @GetMapping("{id}")
    public TagDto getOne(@PathVariable Long id) {
        return tagRepository.findById(id)
                .map(tagMapper::toDto)
                .orElseThrow(NotFoundException.ofMessage("tag.not.found", "id", id));
    }

    @PostMapping()
    public TagDto register(@RequestBody CreateUpdateTagRequest createUpdateTagRequest) {
        Optional<Tag> opt = tagRepository.findByName(createUpdateTagRequest.getName());
        if(opt.isPresent())
            throw BadRequestException.field("name", "tag.name.already.exists");
        Tag tag = new Tag();
        tag.setName(createUpdateTagRequest.getName());
        tag.setDescription(createUpdateTagRequest.getDescription());
        return Optional.of(tag).map(tagRepository::save).map(tagMapper::toDto).get();
    }

    @PutMapping("{id}")
    public TagDto updateTag(@RequestBody TagDto dto, @PathVariable Long id) {
        final Tag tag = tagRepository.findById(id)
                .orElseThrow(NotFoundException.ofMessage("tag.not.found", "id", id));
        tag.setDescription(dto.getDescription());
        tag.setName(dto.getName());
        return Optional.of(tag).map(tagRepository::save).map(tagMapper::toDto).get();
    }

    @DeleteMapping("{id}")
    @AdminApi
    public ResponseEntity<?> deleteTag(@PathVariable Long id) {
        tagRepository.findById(id).ifPresent(tagRepository::delete);
        return ResponseEntity.noContent().build();
    }

}
