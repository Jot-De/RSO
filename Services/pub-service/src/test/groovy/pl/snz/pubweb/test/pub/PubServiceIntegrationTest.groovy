package pl.snz.pubweb.test.pub

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.TestPropertySource
import pl.snz.pubweb.pub.PubwebApp
import pl.snz.pubweb.pub.config.JacksonConfig
import pl.snz.pubweb.pub.config.JpaConfig
import pl.snz.pubweb.security.user.UserPrincipal
import spock.lang.Specification

@ComponentScan("pl.snz.pubweb")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = [PubwebApp.class, JacksonConfig.class, JpaConfig.class])
@ActiveProfiles("it")
@TestPropertySource("classpath:local.properties")
class PubServiceIntegrationTest extends Specification {

    protected def adminSetup() {
        UserPrincipal userDetails = UserPrincipal.builder()
                .id(1l)
                .login('log')
                .password('xd')
                .roles(['ADMIN', 'ADMIN_ROLE'])
                .permissions(Collections.emptySet())
                .build()
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication)
    }

    protected def normalUserSetup() {
        UserPrincipal userDetails = UserPrincipal.builder()
                .id(1l)
                .login('log')
                .password('xd')
                .roles([])
                .permissions(Collections.emptySet())
                .build()
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, (Object)null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication)
    }

    def 'Context loads'() {
        expect:
        true
    }

}
