package pl.snz.pubweb.test.review;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import pl.snz.pubweb.review.ReviewApp;
import pl.snz.pubweb.review.container.JacksonConfig;
import pl.snz.pubweb.review.container.JpaConfig;
import pl.snz.pubweb.review.container.SwaggerConfig;
import pl.snz.pubweb.security.user.UserPrincipal;
import pl.snz.pubweb.test.MockSecurityConfig;

import java.util.Arrays;
import java.util.Collections;

@ComponentScan("pl.snz.pubweb")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@RunWith(SpringRunner.class)
@ContextConfiguration(classes = {ReviewApp.class, MockSecurityConfig.class, JacksonConfig.class, JpaConfig.class, SwaggerConfig.class})
@ActiveProfiles("it")
@TestPropertySource("classpath:local.properties")
public class ReviewServiceIntegrationTest  {

    protected void adminSetup() {
        UserPrincipal userDetails = UserPrincipal.builder()
                .id(1l)
                .login("log")
                .password("xd")
                .roles(Arrays.asList("ADMIN", "ADMIN_ROLE"))
                .permissions(Collections.emptySet())
                .build();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, (Object) null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    protected void normalUserSetup() {
        UserPrincipal userDetails = UserPrincipal.builder()
                .id(1l)
                .login("log")
                .password("xd")
                .roles(Collections.emptyList())
                .permissions(Collections.emptySet())
                .build();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, (Object) null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }


}
