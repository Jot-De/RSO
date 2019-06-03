package pl.snz.pubweb.pub.module.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.picture.PictureMapper;
import pl.snz.pubweb.pub.module.picture.dto.PictureDto;
import pl.snz.pubweb.pub.module.picture.dto.PictureDtoWithData;
import pl.snz.pubweb.pub.module.picture.model.Picture;
import pl.snz.pubweb.pub.module.pub.PubService;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestAcceptanceResponse;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestDto;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestInfo;
import pl.snz.pubweb.pub.module.request.mapper.PubRegistrationRequestMapper;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;
import pl.snz.pubweb.pub.module.request.validation.OpenRegistrationRequestsLimitValidator;
import pl.snz.pubweb.security.RequestSecurityContextProvider;
import pl.snz.pubweb.security.annotations.AdminApi;

import javax.validation.Valid;
import java.util.Optional;

@RestController
@RequestMapping("request")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PubRequestController {

    private final RegistrationRequestRepository repo;
    private final PubRegistrationRequestSpecs specs;
    private final PubRegistrationRequestMapper mapper;
    private final OpenRegistrationRequestsLimitValidator validator;
    private final PubService pubService;
    private final PubRegistrationRequestService service;
    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final PictureMapper pictureMapper;

    @GetMapping("/pending")
    public Page<PubRegistrationRequestInfo> getPending(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "20") int size, @RequestParam(required = false) Long userId) {
        return searchWithSpec(page, size, userId, specs.pending());
    }

    @GetMapping("/accepted")
    public Page<PubRegistrationRequestInfo> getAccepted(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "20") int size, @RequestParam(required = false) Long userId) {
        return searchWithSpec(page, size, userId, specs.accepted());
    }

    @GetMapping("/rejected")
    public Page<PubRegistrationRequestInfo> getRejected(@RequestParam(required = false, defaultValue = "0") int page, @RequestParam(required = false, defaultValue = "20") int size, @RequestParam(required = false) Long userId) {
        return searchWithSpec(page, size, userId, specs.rejected());
    }

    @GetMapping("{id}")
    public PubRegistrationRequestInfo getOne(@PathVariable Long id) {
        return repo.findById(id).map(mapper::toDto)
                .orElseThrow(NotFoundException.ofMessage("request.not.found", "id", id));
    }

    private Page<PubRegistrationRequestInfo> searchWithSpec(int page, int size, Long userId, Specification<PubRegistrationRequest> spec) {
        if(userId != null)
            spec = spec.and(specs.forUser(userId));

        return repo.findAll(spec, PageRequest.of(page, size)).map(mapper::toDto);
    }

    @PostMapping
    @Transactional
    public PubRegistrationRequestInfo add(@RequestBody @Valid PubRegistrationRequestDto request) {
        validator.validate(requestSecurityContextProvider.getPrincipal().getId(), request);
        final PubRegistrationRequest entity = mapper.toEntity(request, requestSecurityContextProvider.getPrincipal().getId());

        return Optional.of(entity).map(repo::save).map(mapper::toDto).get();
    }

    @PutMapping("{requestId}/picture")
    @Transactional
    public PictureDto addPicture(@PathVariable Long requestId, @RequestBody @Valid PictureDtoWithData dto) {
        final PubRegistrationRequest request = repo.findOrThrow(requestId);
        if(!requestSecurityContextProvider.getPrincipal().getId().equals(request.getUserId())) {
            throw BadRequestException.general("add.picutre.not.user.context");
        }
        final Picture picture = pictureMapper.toEntity(dto);
        if(request.getPicture() == null) {
            request.setPicture(picture);
            picture.setRequest(request);
        } else {
            request.getPicture().setBytes(picture.getBytes());
            request.getPicture().setDescription(picture.getDescription());
            request.getPicture().setFormat(picture.getFormat());
            request.getPicture().setName(picture.getName());
        }
        repo.save(request);

        return pictureMapper.toInfo(request.getPicture());
    }

    @AdminApi
    @GetMapping("{requestId}/picture")
    public PictureDtoWithData getPicture(@PathVariable  Long requestId) {
        final PubRegistrationRequest request = repo.findOrThrow(requestId);

        return Optional.ofNullable(request.getPicture())
                .map(pictureMapper::toData)
                .orElseThrow(NotFoundException.ofMessage("request.picture.not.bound", "id", requestId));
    }

    @Transactional
    @PostMapping("{requestId}/accept")
    @AdminApi
    public PubRegistrationRequestAcceptanceResponse accept(@PathVariable Long requestId) {
        final PubRegistrationRequest request = repo.findOrThrow(requestId);
        if(!request.getStatus().equals(PubRegistrationStatus.REGISTERED)) {
            return new PubRegistrationRequestAcceptanceResponse(request.getPub().getId());
        } else if(request.getStatus().equals(PubRegistrationStatus.REJECTED)) {
            throw BadRequestException.general("pub.already.rejected");
        }
        final Pub pub = pubService.createFromRequest(request);
        service.setAccepted(request, pub);

        return new PubRegistrationRequestAcceptanceResponse(pub.getId());
    }

    @PostMapping("{requestId}/cancel")
    @AdminApi
    public PubRegistrationRequestInfo cancel(@PathVariable Long requestId) {
        return repo.findById(requestId).map(service::setCancelled).map(mapper::toDto)
                .orElseThrow(NotFoundException.ofMessage("request.not.found", "id", requestId));
    }

}
