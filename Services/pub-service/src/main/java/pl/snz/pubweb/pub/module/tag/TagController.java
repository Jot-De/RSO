package pl.snz.pubweb.pub.module.tag;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.pub.module.tag.dto.CreateUpdateTagRequest;
import pl.snz.pubweb.pub.module.tag.dto.TagDto;
import pl.snz.pubweb.pub.module.tag.model.Tag;
import pl.snz.pubweb.security.RequestSecurityContextProvider;
import pl.snz.pubweb.security.SecurityService;
import pl.snz.pubweb.security.annotations.AdminApi;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/tag")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class TagController {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    private final TagSearchSpecs tagSearchSpecs;
    private final SecurityService securityService;
    private final TagSubscriptionRepo tagSubscriptionRepo;
    private final RequestSecurityContextProvider requestSecurityContextProvider;

    @GetMapping
    public Page<TagDto> getAll(@RequestParam(required = false, defaultValue = "0") int page,
                               @RequestParam(required = false, defaultValue = "50") int size,
                               @RequestParam(required = false) String nameLike) {

        return tagRepository.findAll(tagSearchSpecs.searchSpec(nameLike),
                PageRequest.of(page, size))
                .map(tagMapper::toDto);
    }

    @GetMapping("{id}")
    public TagDto getOne(@PathVariable Long id) {
        return Just.of(tagRepository.findOrThrow(id)).map(tagMapper::toDto).val();
    }

    @GetMapping("/subscribed")
    public List<TagDto> getSubscriptions() {
        final long userId = requestSecurityContextProvider.principalId();

        return Mappers.list(tagSubscriptionRepo.findForUser(userId), tagMapper::toDto);
    }

    @PostMapping("{id}/subscribe")
    public TagDto subscribe(@PathVariable Long id) {
        final Tag tag = tagRepository.findOrThrow(id);
        final Long userId = requestSecurityContextProvider.principalId();
        tagSubscriptionRepo.addTag(userId, tag);

        return Just.of(tagRepository.save(tag)).map(tagMapper::toDto).val();
    }

    @PostMapping("{id}/cancelSubscription")
    public TagDto cancelSubscription(@PathVariable Long id) {
        final Tag tag = tagRepository.findOrThrow(id);
        final Long userId = requestSecurityContextProvider.principalId();
        tagSubscriptionRepo.removeTag(userId, tag);

        return Just.of(tagRepository.save(tag)).map(tagMapper::toDto).val();
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
