package pl.snz.pubweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import pl.snz.pubweb.user.development.DevelopmentConfigurer;

import java.util.Arrays;

@SpringBootApplication
public class PubwebUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubwebUserServiceApplication.class, args);
    }

    @Autowired
    private DevelopmentConfigurer developmentConfigurer;
}
