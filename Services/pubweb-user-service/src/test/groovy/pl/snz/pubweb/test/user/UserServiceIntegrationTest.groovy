package pl.snz.pubweb.test.user

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import pl.snz.pubweb.user.PubwebUserServiceApplication
import pl.snz.pubweb.user.container.config.JacksonConfig
import pl.snz.pubweb.user.container.config.JpaConfig
import spock.lang.Specification

@ComponentScan("pl.snz.pubweb")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [PubwebUserServiceApplication.class, JacksonConfig.class, JpaConfig.class])
@ActiveProfiles("it")
@TestPropertySource("classpath:local.properties")
class UserServiceIntegrationTest extends Specification {

    def 'Context loads'() {
        expect:
        true
    }

}
