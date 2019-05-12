package pl.snz.pubweb.review;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@ComponentScan("pl.snz.pubweb") /** Add component scan to scan reviews */
@SpringBootApplication
public class ReviewApp {

    public static void main(String[] args) {
        SpringApplication.run(ReviewApp.class);
    }
}
