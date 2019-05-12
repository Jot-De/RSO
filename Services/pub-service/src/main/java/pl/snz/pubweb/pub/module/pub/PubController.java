package pl.snz.pubweb.pub.module.pub;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.pub.dto.PubDto;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.pub.presentation.PubMapper;
import pl.snz.pubweb.security.annotations.AdminApi;

import java.util.List;

@RestController
@RequestMapping("/pub")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class PubController {

    private final PubRepository pubRepository;
    private final PubSearchSpecifications pubSearchSpecifications;
    private final PubMapper pubMapper;
    private final PubService pubService;


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









}
