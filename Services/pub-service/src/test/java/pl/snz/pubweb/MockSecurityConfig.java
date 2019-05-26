package pl.snz.pubweb;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import pl.snz.pubweb.security.RequestSecurityContextProvider;
import pl.snz.pubweb.security.user.UserPrincipal;

import java.util.Collections;

@Profile("it")
@Configuration
public class MockSecurityConfig {

    @Bean
    @Primary
    public RequestSecurityContextProvider mockRequestSecurityContextProvider() {
        return new RequestSecurityContextProvider() {
            @Override
            public UserPrincipal getPrincipal() {
                return UserPrincipal.builder()
                        .id(1l)
                        .login("log")
                        .password("pass")
                        .roles(Collections.emptySet())
                        .permissions(Collections.emptySet())
                        .build();
            }

            @Override
            public long principalId() {
                return 1l;
            }
        };
    }
}
