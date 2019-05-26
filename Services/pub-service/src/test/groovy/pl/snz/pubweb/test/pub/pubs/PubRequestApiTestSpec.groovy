package pl.snz.pubweb.test.pub.pubs

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.util.ResourceUtils
import pl.snz.pubweb.pub.module.common.dto.AddressDto
import pl.snz.pubweb.pub.module.picture.dto.PictureDto
import pl.snz.pubweb.pub.module.picture.dto.PictureDtoWithData
import pl.snz.pubweb.pub.module.pub.PubRepository
import pl.snz.pubweb.pub.module.pub.model.Pub
import pl.snz.pubweb.pub.module.request.PubRequestController
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestAcceptanceResponse
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestDto
import pl.snz.pubweb.pub.module.request.dto.PubRegistrationRequestInfo
import pl.snz.pubweb.pub.module.request.model.PubRegistrationStatus
import pl.snz.pubweb.test.pub.PubServiceIntegrationTest

import java.nio.file.Files

class PubRequestApiTestSpec extends PubServiceIntegrationTest {

    @Autowired
    private PubRequestController requestController
    @Autowired
    private PubRepository pubRepository

    def 'Given non existing pub request is added properly'() {
        given:
        final PubRegistrationRequestDto requestDto = PubRegistrationRequestDto.builder()
                .description(description)
                .address(address)
                .name(name)
                .build()

        when:
        final PubRegistrationRequestInfo result = requestController.add(requestDto)

        then:
        description.equals(result.getDescription())
        address.equals(result.getAddress())
        name.equals(result.getName())
        expectedStatus.equals(result.getStatus())

        where:
        address                       | name | description | expectedStatus
        new AddressDto('a', 'b', 'c') | 'ab' | 'descr'     | PubRegistrationStatus.PENDING
    }

    def 'Test add, get request picture'() {
        given:
        adminSetup()
        String base64 = Base64.getMimeEncoder().encodeToString(Files.readAllBytes(ResourceUtils.getFile("classpath:x.jpg").toPath()))
        PictureDtoWithData dto = new PictureDtoWithData()
        dto.setBase64Picture(base64)
        dto.setDataFormat(format)
        dto.setDescription(description)

        when:
        final PictureDto picture = requestController.addPicture(1l, dto)
        final Long id = picture.getId()
        final PictureDtoWithData result = requestController.getPicture(1l)

        then:
        base64.equals(result.getBase64Picture())
        format.equals(result.getDataFormat())
        description.equals(result.getDescription())

        where:
        format | description
        'jpg'  | 'desc'
        'png'  | 'desc2'

    }

    def 'Test request acceptance'() {
        given:
        adminSetup()
        final PubRegistrationRequestDto request = PubRegistrationRequestDto.builder()
                .address(address)
                .description(desc)
                .name(name)
                .build()

        final PubRegistrationRequestInfo registrationResult = requestController.add(request)

        when:
        final PubRegistrationRequestAcceptanceResponse acceptanceResult = requestController.accept(registrationResult.getId())
        final Optional<Pub> pub = pubRepository.findById(acceptanceResult.getPubId())

        then:
        acceptanceResult.getPubId() != null
        pub.isPresent()
        desc.equals(pub.get().getDescription())
        name.equals(pub.get().getName())

        where:
        address                       | desc   | name
        new AddressDto('a', 'b', 'c') | 'desc' | Long.toHexString(new Random().nextLong())
    }

    def 'Test request acceptance with pictrue'() {
        given:
        adminSetup()
        final PubRegistrationRequestDto request = PubRegistrationRequestDto.builder()
                .address(address)
                .description(desc)
                .name(name)
                .build()

        final PubRegistrationRequestInfo registrationResult = requestController.add(request)

        final String base64 = Base64.getMimeEncoder().encodeToString(Files.readAllBytes(ResourceUtils.getFile("classpath:x.jpg").toPath()))
        final PictureDtoWithData pictureDto = new PictureDtoWithData()
        pictureDto.setBase64Picture(base64)
        pictureDto.setDataFormat('jpg')
        pictureDto.setDescription('desc')

        final PictureDto pictureAddResult = requestController.addPicture(registrationResult.getId(), pictureDto)

        when:
        final PubRegistrationRequestAcceptanceResponse acceptanceResult = requestController.accept(registrationResult.getId())
        final Optional<Pub> pub = pubRepository.findById(acceptanceResult.getPubId())

        then:
        acceptanceResult.getPubId() != null
        pub.isPresent()
        desc.equals(pub.get().getDescription())
        name.equals(pub.get().getName())
        pub.get().getPictures().size() == 1
        pub.get().getPictures().iterator().next().getId() == pictureAddResult.getId()

        where:
        address                       | desc   | name
        new AddressDto('a', 'b', 'c') | 'desc' | Long.toHexString(new Random().nextLong())
    }

}
