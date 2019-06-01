package pl.snz.pubweb.test.pub.pubs.pub

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import pl.snz.pubweb.pub.module.pub.PubRepository
import pl.snz.pubweb.pub.module.pub.PubSearchSpecifications
import pl.snz.pubweb.pub.module.pub.model.Pub
import pl.snz.pubweb.test.pub.PubServiceIntegrationTest
import spock.lang.Unroll

class PubSpecificationsTest extends PubServiceIntegrationTest {

    @Autowired
    private PubRepository pubRepository
    @Autowired
    private PubSearchSpecifications pubSpecifications

    @Unroll
    @Sql(scripts = ['/sql/pub-search-spec-test.sql'])
    def 'Test search specifications name[#name] city[#city] tags[#tags] expectedSize[#expectedSize]'() {
        when:
        final List<Pub> result = pubRepository.findAll(pubSpecifications.getSearchSpec(name, city, tags))

        then:
        result.size() == expectedSize

        where:
        name    | city       | tags | expectedSize
        'pub12' | null       | null | 1
        'pub'   | null       | null | 2
        'pub32' | null       | null | 1
        'PUB'   | null       | null | 2
        null    | 'War'      | null | 1
        null    | 'war'      | null | 1
        null    | 'arszawa'  | null | 1
        null    | 'WARSZAWA' | null | 1
        null    | null       | [1l] | 1
        null    | null       | [2l] | 2
        null    | null       | [3l] | 0

    }


}
