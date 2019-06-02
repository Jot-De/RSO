package pl.snz.pubweb.test.pub.pubs.pub

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.test.context.jdbc.Sql
import pl.snz.pubweb.commons.util.Lists
import pl.snz.pubweb.commons.util.function.Pair
import pl.snz.pubweb.pub.module.pub.PubRepository
import pl.snz.pubweb.pub.module.pub.PubSearchSpecifications
import pl.snz.pubweb.pub.module.pub.model.Pub
import pl.snz.pubweb.test.pub.PubServiceIntegrationTest
import spock.lang.Unroll

import java.time.LocalDate
import java.util.function.BiPredicate
import java.util.function.Predicate

import static java.time.LocalDate.parse

class PubSpecificationsTest extends PubServiceIntegrationTest {

    @Autowired
    private PubRepository pubRepository
    @Autowired
    private PubSearchSpecifications pubSpecifications

    @Unroll
    @Sql(scripts = ['/sql/pub-search-spec-test.sql'])
    def 'Test search specifications name[#name] city[#city] tags[#tags] addedAfter[#addedAfter] addedBefore[#addedBefore] expectedSize[#expectedSize]'() {
        given:
        BiPredicate<Pub, Pub> pubsPredicate = { prev, next ->
            LocalDate addedPrev = prev.getAdded()
            LocalDate addedNext = next.getAdded()
            return addedPrev.isAfter(addedNext) || addedPrev.isEqual(addedNext)
        }
        Predicate<Pair> pairsPredicate = { p -> p.matches(pubsPredicate)}

        when:
        final List<Pub> result = pubRepository.findAll(pubSpecifications.getSearchSpec(name, city, addedAfter, addedBefore, tags))

        then:
        result.size() == expectedSize
        Lists.toAdjacentPairs(result).allMatch(pairsPredicate)


        where:
        name    | city       | tags | addedAfter          | addedBefore         | expectedSize
        'pub12' | null       | null | null                | null                | 1
        'pub'   | null       | null | null                | null                | 2
        'pub32' | null       | null | null                | null                | 1
        'PUB'   | null       | null | null                | null                | 2
        null    | 'War'      | null | null                | null                | 1
        null    | 'war'      | null | null                | null                | 1
        null    | 'arszawa'  | null | null                | null                | 1
        null    | 'WARSZAWA' | null | null                | null                | 1
        null    | null       | [1l] | null                | null                | 1
        null    | null       | [2l] | null                | null                | 2
        null    | null       | [3l] | null                | null                | 0
        null    | null       | null | parse('2019-05-17') | null                | 2
        null    | null       | null | parse('2019-05-19') | null                | 1
        null    | null       | null | parse('2019-05-21') | null                | 0
        null    | null       | null | null                | parse('2019-05-17') | 0
        null    | null       | null | null                | parse('2019-05-19') | 1
        null    | null       | null | null                | parse('2019-05-21') | 2

    }


}
