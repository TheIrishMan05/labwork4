package se.ifmo.lab4backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@ComponentScan(basePackages = {"se.ifmo", "se.ifmo.lab4backend"})
@EnableTransactionManagement
public class Lab4BackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(Lab4BackendApplication.class, args);
    }

}
