package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.UserLoginDto;
import org.labonnefranquette.data.model.User;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
public class UserLoginDTOTest {

    private String username;
    private String password;
    private UserLoginDto userLoginDto;
    private DtoTools dtoTools;

    @BeforeEach
    public void setUp() {
        username = "testUser";
        password = "testPassword";
        userLoginDto = new UserLoginDto();
        userLoginDto.setUsername(username);
        userLoginDto.setPassword(password);
        dtoTools = new DtoTools();
    }

    @Test
    public void testUserLoginDtoNotNull() {
        assertNotNull(userLoginDto);
    }

    @Test
    public void testUsername() {
        assertEquals(username, userLoginDto.getUsername());
    }

    @Test
    public void testPassword() {
        assertEquals(password, userLoginDto.getPassword());
    }

    @Test
    public void testConvertedUserNotNull() {
        User user = dtoTools.convertToEntity(userLoginDto, User.class);
        assertNotNull(user);
    }

    @Test
    public void testConvertedUsername() {
        User user = dtoTools.convertToEntity(userLoginDto, User.class);
        assertEquals(username, user.getUsername());
    }

    @Test
    public void testConvertedPassword() {
        User user = dtoTools.convertToEntity(userLoginDto, User.class);
        assertEquals(password, user.getPassword());
    }
}