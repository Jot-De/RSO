package pl.snz.pubweb.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("pl.snz.pubweb")
@SpringBootApplication
@EnableDiscoveryClient
public class PubwebUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(PubwebUserServiceApplication.class, args);
    }

}
