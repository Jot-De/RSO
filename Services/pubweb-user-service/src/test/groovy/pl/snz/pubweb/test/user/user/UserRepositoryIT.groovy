package pl.snz.pubweb.test.user.user

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.jdbc.Sql
import pl.snz.pubweb.test.user.UserServiceIntegrationTest
import pl.snz.pubweb.user.module.user.UserRepository
import pl.snz.pubweb.user.module.user.model.User
import spock.lang.Unroll

class UserRepositoryIT extends UserServiceIntegrationTest {

    @Autowired
    private UserRepository userRepository;

    @Unroll
    @Sql(scripts = ['/sql/user-search-spec-test.sql'])
    def 'Test user search spec for name[#name]'() {
        when:
        final Page<User> users = userRepository.search(name, PageRequest.of(0, 20))

        then:
        users.numberOfElements == expectedSize

        where:
        name        | expectedSize
        'Luk'       | 2
        'lukg'      | 2
        'lukgrebo'  | 2
        'LUKGREBO'  | 2
        'lukgrebo2' | 1
    }
}
