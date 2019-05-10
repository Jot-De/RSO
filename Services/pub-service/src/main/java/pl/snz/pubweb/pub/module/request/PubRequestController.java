package pl.snz.pubweb.pub.module.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.pub.PubService;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestAcceptanceResponse;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestDto;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestInfo;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;
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

    private Page<PubRegistrationRequestInfo> searchWithSpec(int page, int size, Long userId, Specification<PubRegistrationRequest> spec) {
        if(userId != null)
            spec = spec.and(specs.forUser(userId));
        return repo.findAll(spec, PageRequest.of(page, size)).map(mapper::toDto);
    }

    @PostMapping
    public PubRegistrationRequestInfo add(@RequestBody @Valid PubRegistrationRequestDto request) {
        validator.validate(requestSecurityContextProvider.getPrincipal().getId(), request);
        PubRegistrationRequest entity = mapper.toEntity(request, requestSecurityContextProvider.getPrincipal().getId());
        return Optional.of(entity).map(repo::save).map(mapper::toDto).get();
    }

    @PostMapping("{id}/accept")
    @AdminApi
    public PubRegistrationRequestAcceptanceResponse accept(@PathVariable Long id) {
        final PubRegistrationRequest request = repo.findOrThrow(id);
        final Pub pub = pubService.createFromRequest(request);
        service.setAccepted(request, pub);
        return new PubRegistrationRequestAcceptanceResponse(pub.getId());
    }

    @PostMapping("{id}/cancel")
    @AdminApi
    public PubRegistrationRequestInfo cancel(@PathVariable Long id) {
        return repo.findById(id).map(service::setCancelled).map(mapper::toDto)
                .orElseThrow(NotFoundException.ofMessage("request.not.found", "id", id));
    }

}
