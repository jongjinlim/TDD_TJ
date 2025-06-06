package sample.caftkiosk.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
public class CaftkioskApplication {

    public static void main(String[] args) {
        SpringApplication.run(CaftkioskApplication.class, args);
    }
}
