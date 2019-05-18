package pl.snz.pubweb.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import pl.snz.pubweb.user.container.init.InitialConfiguration;

@ComponentScan("pl.snz.pubweb")
@SpringBootApplication
@EnableDiscoveryClient
public class PubwebUserServiceApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(PubwebUserServiceApplication.class, args);
    }

    @Autowired private InitialConfiguration initialConfiguration;

    @Override
    public void run(String... args) throws Exception {
        initialConfiguration.run();
    }
}
