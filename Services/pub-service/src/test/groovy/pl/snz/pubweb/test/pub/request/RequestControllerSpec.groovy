package pl.snz.pubweb.test.pub.request

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.transaction.annotation.Transactional
import pl.snz.pubweb.pub.module.common.dto.AddressDto
import pl.snz.pubweb.pub.module.pub.PubRepository
import pl.snz.pubweb.pub.module.pub.model.Pub
import pl.snz.pubweb.pub.module.request.PubRequestController
import pl.snz.pubweb.pub.module.request.RegistrationRequestRepository
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestAcceptanceResponse
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestDto
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestInfo
import pl.snz.pubweb.pub.module.request.model.PubRegistrationRequest
import pl.snz.pubweb.test.pub.PubServiceIntegrationTest

@Transactional
class RequestControllerSpec extends PubServiceIntegrationTest {

    @Autowired private PubRequestController controller
    @Autowired private RegistrationRequestRepository registrationRequestRepository
    @Autowired private PubRepository pubRepository
    def setupSpec() {

    }

    def 'Test request and then accept'() {
        given:
        adminSetup()
        PubRegistrationRequestDto request = new PubRegistrationRequestDto()
        request.setDescription('description')
        request.setAddress(new AddressDto('city', 'some fine street', 'number'))
        request.setTags([1l,2l,3l])
        request.setName('name')

        when:
        PubRegistrationRequestInfo response = controller.add(request)
        PubRegistrationRequestAcceptanceResponse accept = controller.accept(response.getId())
        Pub pub = pubRepository.findById(accept.getPubId()).get()

        then:
        PubRegistrationRequest dbRequest = registrationRequestRepository.findById(response.getId()).get()
        dbRequest.getTags().size() == 3
        pub.getTags().size() == 3
    }


    def cleanupSpec() {

    }
}
