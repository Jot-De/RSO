package pl.snz.pubweb.pub.module.pub;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PubServiceImpl implements PubService {

    private final PubRepository repository;

    @Override
    public Pub createFromRequest(PubRegistrationRequest request) {
        Pub pub = new Pub();
        pub.setAddress(request.getAddress());
        pub.setAdded(LocalDate.now());
        pub.setName(request.getName());
        pub.setDescription(request.getDescription());
        Optional.ofNullable(request.getPicture()).ifPresent(pub.getPictures()::add);
        pub.getTags().addAll(request.getTags());
        return repository.save(pub);
    }

}
