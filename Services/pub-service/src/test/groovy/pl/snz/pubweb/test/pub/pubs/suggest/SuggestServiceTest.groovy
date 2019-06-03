package pl.snz.pubweb.test.pub.pubs.suggest

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import pl.snz.pubweb.pub.module.suggest.dto.PubSuggestion
import pl.snz.pubweb.pub.module.suggest.service.SuggestService
import pl.snz.pubweb.test.pub.PubServiceIntegrationTest
import spock.lang.Unroll

class SuggestServiceTest extends PubServiceIntegrationTest {


    @Autowired
    private SuggestService suggestService

    @Unroll
    @Sql(scripts = ['/sql/pub-suggest-service-test.sql'])
    def 'Test suggestions'() {
        when:
        final List<PubSuggestion> suggestions = suggestService.getForUser(userId)

        then:
        suggestions.size() == expectedSize

        where:
        userId | expectedSize
        1      | 3
        2      | 2


    }


}
