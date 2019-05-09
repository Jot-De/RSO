package pl.snz.pubweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import pl.snz.pubweb.user.development.DevelopmentConfigurer;

@SpringBootApplication
//@EnableDiscoveryClient
public class PubwebUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubwebUserServiceApplication.class, args);
    }

    @Autowired
    private DevelopmentConfigurer developmentConfigurer;
}
