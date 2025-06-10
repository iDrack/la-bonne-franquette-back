package org.labonnefranquette.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Objects;

@SpringBootApplication
public class LaBonneFranquetteApplication {

    public static void main(String[] args) {
        System.setProperty("SPRING_MAIL_ACCOUNT", Objects.requireNonNull(System.getenv("SPRING_MAIL_ACCOUNT")));
        System.setProperty("SPRING_MAIL_PASSWORD", Objects.requireNonNull(System.getenv("SPRING_MAIL_PASSWORD")));
        System.setProperty("BDD_USERNAME", Objects.requireNonNull(System.getenv("BDD_USERNAME")));
        System.setProperty("BDD_PASSWORD", Objects.requireNonNull(System.getenv("BDD_PASSWORD")));
        System.setProperty("PORT", Objects.requireNonNull(System.getenv("PORT")));
        SpringApplication.run(LaBonneFranquetteApplication.class, args);
        System.out.println("Swagger documentation : " + "http://localhost:" + System.getenv("PORT") + "/swagger-ui.html");
    }
}
