package org.labonnefranquette.data;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

import java.util.Objects;

@SpringBootApplication
@EnableCaching
public class LaBonneFranquetteApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().load();
		System.setProperty("SPRING_MAIL_ACCOUNT", Objects.requireNonNull(dotenv.get("SPRING_MAIL_ACCOUNT")));
		System.setProperty("SPRING_MAIL_PASSWORD", Objects.requireNonNull(dotenv.get("SPRING_MAIL_PASSWORD")));
		SpringApplication.run(LaBonneFranquetteApplication.class, args);
		System.out.println("Swagger documentation : " + "http://localhost:8080/swagger-ui.html");
	}
}
