package org.labonnefranquette.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan({"org.labonnefranquette"})
@EntityScan("org.labonnefranquette")
@EnableJpaRepositories("org.labonnefranquette.data")
public class LaBonneFranquetteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaBonneFranquetteApplication.class, args);
	}

}
