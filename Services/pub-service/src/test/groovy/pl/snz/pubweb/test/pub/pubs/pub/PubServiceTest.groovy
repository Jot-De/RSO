package pl.snz.pubweb.test.pub.pubs.pub

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import org.springframework.transaction.annotation.Transactional
import pl.snz.pubweb.pub.module.pub.PubRepository
import pl.snz.pubweb.pub.module.pub.PubService
import pl.snz.pubweb.pub.module.pub.model.Pub
import pl.snz.pubweb.pub.module.request.RegistrationRequestRepository
import pl.snz.pubweb.pub.module.tag.TagRepository
import pl.snz.pubweb.test.pub.PubServiceIntegrationTest

class PubServiceTest extends PubServiceIntegrationTest {

    @Autowired
    private PubService pubService
    @Autowired
    private PubRepository pubRepository
    @Autowired
    private TagRepository tagRepository;
    @Autowired
    private RegistrationRequestRepository requestRepository;

    @Transactional
    @Sql(scripts = "/sql/pub-delete-test.sql")
    'Def test full data pub deletion'() {
        given:
        final Pub pub = pubRepository.findById(1l).get()

        when:
        pubService.delete(pub)

        then:
        !pubRepository.findById(1l).isPresent()
        tagRepository.findById(1l).isPresent() // Check cascading did not delete tag
        requestRepository.findById(1l).get().getPub() == null
    }
}
