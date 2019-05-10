package pl.snz.pubweb.pub.module.request.validation;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.commons.errors.exception.BadRequestException;
import pl.snz.pubweb.pub.module.request.PubRegistrationRequestSpecs;
import pl.snz.pubweb.pub.module.request.RegistrationRequestRepository;
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestDto;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class OpenRegistrationRequestsLimitValidator {

    private final RegistrationRequestRepository repo;
    private final PubRegistrationRequestSpecs specs;

    public void validate(Long userId, @Valid PubRegistrationRequestDto request) {
        Specification<PubRegistrationRequest> spec = specs.forUser(userId).and(specs.pending());
        if(repo.findAll(spec).size() > 10)
            throw BadRequestException.general("too.many.open.registration.requests");
        if(repo.findOne(specs.nameAndCity(request.getName(), request.getAddress().getCity())).isPresent())
            throw BadRequestException.general("pub.already.requested");
    }
}
