package pl.snz.pubweb.pub.module.visit;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.commons.util.Just;
import pl.snz.pubweb.commons.util.Mappers;
import pl.snz.pubweb.pub.module.pub.PubRepository;
import pl.snz.pubweb.pub.module.visit.dto.VisitDto;
import pl.snz.pubweb.pub.module.visit.dto.VisitStatusDto;
import pl.snz.pubweb.pub.module.visit.dto.VisitWishDto;
import pl.snz.pubweb.pub.module.visit.dto.VisitedPubDto;
import pl.snz.pubweb.pub.module.visit.model.Visit;
import pl.snz.pubweb.pub.module.visit.model.VisitStatus;
import pl.snz.pubweb.security.RequestSecurityContextProvider;
import pl.snz.pubweb.security.SecurityService;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/visits")
@RequiredArgsConstructor
public class VisitController {
    private final RequestSecurityContextProvider requestSecurityContextProvider;
    private final SecurityService securityService;
    private final VisitRepository repo;
    private final VisitSpecifications specs;
    private final VisitMapper mapper;
    private final PubRepository pubRepo;

    @GetMapping("/visited")
    public Page<VisitedPubDto> getVisited(@RequestParam(required = false, defaultValue = "0") int page,
                                          @RequestParam(required = false, defaultValue = "20") int size,
                                          @RequestParam(required = false) Long pubId,
                                          @RequestParam(required = false) Long userId) {
        userId = userId != null ? userId :  requestSecurityContextProvider.getPrincipal().getId();

        return repo.findAll(specs.visited(userId, pubId), PageRequest.of(page, size)).map(mapper::toVisitedDto);
    }

    @GetMapping("/wish")
    public Page<VisitWishDto> getWishes(@RequestParam(required = false, defaultValue = "0") int page,
                                        @RequestParam(required = false, defaultValue = "20") int size,
                                        @RequestParam(required = false) Long pubId,
                                        @RequestParam(required = false) Long userId) {
        userId = userId != null ? userId :  requestSecurityContextProvider.getPrincipal().getId();

        return repo.findAll(specs.wishes(userId, pubId), PageRequest.of(page, size)).map(mapper::toWishDto);
    }

    @GetMapping("/status")
    public List<VisitStatusDto> checkIfVisited(@RequestParam List<Long> pubIds,  @RequestParam Long userId) {
        return pubIds.isEmpty() ? Collections.emptyList() : Mappers.list(repo.findAll(specs.statuses(userId, pubIds)), mapper::toStatusDto);
    }


    @GetMapping("{id}")
    public VisitDto getOne(@PathVariable Long id) {
        final Visit visit = repo.findById(id).orElseThrow(NotFoundException.ofMessage("visit.not.found", "id", id));

        return mapper.toVisitDto(visit);
    }


    @PostMapping("/visited")
    public VisitedPubDto markVisited(@RequestParam(required = true) Long pubId) {
        final Long userId = requestSecurityContextProvider.getPrincipal().getId();
        final Optional<Visit> opt = repo.findByUserIdAndPubId(userId, pubId);

        Visit visit = opt.isPresent() ? opt.get() : buildVisit(userId, pubId, LocalDate.now(), null);
        visit.setVisitStatus(VisitStatus.VISITED);

        return Just.of(visit).map(repo::save).map(mapper::toVisitedDto).val();
    }

    @PostMapping("/wish")
    public VisitWishDto addToWishList(@RequestParam(required = true) Long pubId) {
        final Long userId = requestSecurityContextProvider.getPrincipal().getId();
        checkNotAddedYet(pubId, userId);
        Visit visit = buildVisit(userId, pubId, null, VisitStatus.WANTS_TO_VISIT);

        return Just.of(visit).map(repo::save).map(mapper::toWishDto).val();
    }

    @DeleteMapping("{id}")
    public ResponseEntity delete(@PathVariable Long id) {
        final Optional<Visit> visitOpt = repo.findById(id);
        if(visitOpt.isPresent()){
            Visit visit = visitOpt.get();
            securityService.requireSelf(visit.getUserId());
            repo.delete(visit);
        }
        return ResponseEntity.ok().build();
    }

    private void checkNotAddedYet(@RequestParam(required = true) Long pubId, Long userId) {
        final Optional<Visit> opt = repo.findByUserIdAndPubId(userId, pubId);
        if(opt.isPresent()) {
            throw BadRequestException.general("pub.already.on.with.list.or.visited");
        }
    }

    private Visit buildVisit(Long userId, Long pubId, LocalDate visited, VisitStatus status) {
        Visit visit = new Visit();
        visit.setPub(pubRepo.findOrThrow(pubId));
        visit.setUserId(userId);
        visit.setVisited(visited);
        visit.setVisitStatus(status);
        return visit;
    }
}
