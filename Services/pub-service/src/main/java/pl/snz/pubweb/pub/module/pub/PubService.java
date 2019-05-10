package pl.snz.pubweb.pub.module.pub;

import pl.snz.pubweb.pub.module.pub.model.Pub;
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest;

public interface PubService {
    Pub createFromRequest(PubRegistrationRequest request);
}
