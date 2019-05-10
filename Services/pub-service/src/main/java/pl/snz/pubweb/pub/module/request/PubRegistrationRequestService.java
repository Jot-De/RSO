package pl.snz.pubweb.pub.module.request;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PubRegistrationRequestService {

    private final RegistrationRequestRepository repo;

    public PubRegistrationRequest setAccepted(PubRegistrationRequest request, Pub pub) {
        request.setPub(pub);
        return setStatusAndSave(request, PubRegistrationStatus.REGISTERED);
    }

    public PubRegistrationRequest setCancelled(PubRegistrationRequest request) {
        return setStatusAndSave(request, PubRegistrationStatus.REJECTED);
    }

    private PubRegistrationRequest setStatusAndSave(PubRegistrationRequest request, PubRegistrationStatus status) {
        request.setStatus(status);
        request.setProcessed(LocalDate.now());
        return repo.save(request);
    }

}
