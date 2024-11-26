package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.UserCreateDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
public class UserCreateDTOTest {

    private String username;
    private String password;
    private UserCreateDto userCreateDto;
    private DtoTools dtoTools;

    @BeforeEach
    public void setUp() {
        username = "testUser";
        password = "testPassword";
        userCreateDto = new UserCreateDto();
        userCreateDto.setUsername(username);
        userCreateDto.setPassword(password);
        dtoTools = new DtoTools();
    }

    @Test
    public void testUserCreateDtoNotNull() {
        assertNotNull(userCreateDto);
    }

    @Test
    public void testUsername() {
        assertEquals(username, userCreateDto.getUsername());
    }

    @Test
    public void testPassword() {
        assertEquals(password, userCreateDto.getPassword());
    }

    @Test
    public void testConvertedUserNotNull() {
        User user = dtoTools.convertToEntity(userCreateDto, User.class);
        assertNotNull(user);
    }

    @Test
    public void testConvertedUsername() {
        User user = dtoTools.convertToEntity(userCreateDto, User.class);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testConvertedPassword() {
        User user = dtoTools.convertToEntity(userCreateDto, User.class);
        assertEquals(password, user.getPassword());
    }

    @Test
    public void testToString() {
        assertEquals("user=testUser.", userCreateDto.toString());
    }
}