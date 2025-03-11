package org.labonnefranquette.data;

import io.github.cdimascio.dotenv.Dotenv;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
class LaBonneFranquetteApplicationTests {

    @BeforeAll
    static void setup() {
        Dotenv dotenv = Dotenv.configure().load();
        System.setProperty("SPRING_MAIL_ACCOUNT", dotenv.get("SPRING_MAIL_ACCOUNT"));
        System.setProperty("SPRING_MAIL_PASSWORD", dotenv.get("SPRING_MAIL_PASSWORD"));
    }



}
