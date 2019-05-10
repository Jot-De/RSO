package pl.snz.pubweb.pub.module.request;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import pl.snz.pubweb.commons.errors.exception.NotFoundException;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;

public interface RegistrationRequestRepository extends JpaRepository<PubRegistrationRequest, Long>, JpaSpecificationExecutor<PubRegistrationRequest> {
    default PubRegistrationRequest findOrThrow(Long id) {
        return this.findById(id).orElseThrow(NotFoundException.ofMessage("request.not.found", "id", id));
    }
}
