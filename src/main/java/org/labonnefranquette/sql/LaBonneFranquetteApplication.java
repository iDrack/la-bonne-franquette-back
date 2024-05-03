package org.labonnefranquette.sql;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan({"org.labonnefranquette"})
@EntityScan("org.labonnefranquette")
@EnableMongoRepositories("org.labonnefranquette.mongo")
@EnableJpaRepositories("org.labonnefranquette.sql")
public class LaBonneFranquetteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaBonneFranquetteApplication.class, args);
	}

}
