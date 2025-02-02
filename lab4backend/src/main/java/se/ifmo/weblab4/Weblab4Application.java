package se.ifmo.weblab4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"se.ifmo", "se.ifmo.weblab4"})
public class Weblab4Application {

    public static void main(String[] args) {
        SpringApplication.run(Weblab4Application.class, args);
    }

}
