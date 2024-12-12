package org.labonnefranquette.data.functional;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Order(1)
@ActiveProfiles("test")
@Tag("functional")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class SetupFunctionalTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Test
    void createOneUserIfNotExists() {
        userRepository.deleteAll();
        // Arrange
        String url = "/api/v1/user/register";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        UserCreateDto userDto = new UserCreateDto();
        userDto.setUsername("testUser1");
        userDto.setPassword("testPassword1");
        HttpEntity<UserCreateDto> request = new HttpEntity<>(userDto, headers);

        // Act
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, request, String.class);

        // Assert
        assertEquals(201, response.getStatusCodeValue());
    }
}
