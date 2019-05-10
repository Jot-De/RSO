package pl.snz.pubweb.pub.module.request;

import lombok.Getter;
import lombok.experimental.Accessors;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import pl.snz.pubweb.pub.module.common.data.Address_;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest_;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus;

@Component
public class PubRegistrationRequestSpecs {

    @Getter @Accessors(fluent = true)
    private final Specification<PubRegistrationRequest> pending = (r,q,cb) ->
            cb.equal(r.get(PubRegistrationRequest_.status), PubRegistrationStatus.PENDING);


    @Getter @Accessors(fluent = true)
    private final Specification<PubRegistrationRequest> accepted = (r,q,cb) ->
         cb.equal(r.get(PubRegistrationRequest_.status), PubRegistrationStatus.REGISTERED);


    @Getter @Accessors(fluent = true)
    private final Specification<PubRegistrationRequest> rejected = (r,q,cb) ->
         cb.equal(r.get(PubRegistrationRequest_.status), PubRegistrationStatus.REJECTED);


    public Specification<PubRegistrationRequest> forUser(Long userId) {
        return (r,q,cb) -> cb.equal(r.get(PubRegistrationRequest_.userId), userId);
    }

    public Specification<PubRegistrationRequest> nameAndCity(String name, String city) {
        return (r,q,cb) -> {
            return cb.and(
              cb.equal(r.get(PubRegistrationRequest_.name), name),
              cb.equal(r.get(PubRegistrationRequest_.address).get(Address_.city), city)
            );
        };
    }

}
