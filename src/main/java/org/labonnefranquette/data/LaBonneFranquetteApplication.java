package org.labonnefranquette.data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class LaBonneFranquetteApplication {

	public static void main(String[] args) {
		SpringApplication.run(LaBonneFranquetteApplication.class, args);
	}
}
