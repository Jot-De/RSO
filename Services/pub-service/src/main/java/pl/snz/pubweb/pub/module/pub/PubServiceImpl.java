package pl.snz.pubweb.pub.module.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.pub.module.picture.PictureRepository;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.RegistrationRequestRepository;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PubServiceImpl implements PubService {

    private final PubRepository repository;
    private final RegistrationRequestRepository requestRepository;
    private final PictureRepository pictureRepository;

    @Override
    public Pub createFromRequest(PubRegistrationRequest request) {
        Pub pub = new Pub();
        pub.setAddress(request.getAddress());
        pub.setAdded(LocalDate.now());
        pub.setName(request.getName());
        pub.setDescription(request.getDescription());
        Optional.ofNullable(request.getPicture()).ifPresent(p ->{
                pub.getPictures().add(p);
                p.setPub(pub);
        });

        pub.getTags().addAll(request.getTags());
        return repository.save(pub);
    }

    @Override
    public void delete(Pub pub) {
        final Optional<PubRegistrationRequest> rqOpt = requestRepository.findByPubId(pub.getId());
        if(rqOpt.isPresent()) {
            final PubRegistrationRequest request = rqOpt.get();
            request.setPub(null);
            request.setStatus(PubRegistrationStatus.PUB_DELETED);
            requestRepository.save(request);
        }
        pub.getPictures().forEach(pictureRepository::delete);
        repository.delete(pub);
    }

}
