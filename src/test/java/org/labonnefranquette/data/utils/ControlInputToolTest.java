
package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
public class ControlInputToolTest {

    @Test
    public void isValidStringSuccessfully() {
        // Arrange
        String validString = "Valid string.";
        // Act
        boolean isValid = ControlInputTool.checkString(validString);
        // Assert
        assertTrue(isValid);
    }

    @Test
    public void isValidStringFail() {
        // Arrange
        String invalidString = "<script>alert('XSS');</script>";
        // Act
        boolean isValid = ControlInputTool.checkString(invalidString);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isValidObjectSuccessfully() {
        // Arrange
        TestObject validObject = new TestObject("Valid string");
        // Act
        boolean isValid = ControlInputTool.checkObject(validObject, TestObject.class);
        // Assert
        assertTrue(isValid);
    }

    @Test
    public void isValidObjectFail() {
        // Arrange
        TestObject invalidObject = new TestObject("<script>alert('XSS');</script>");
        // Act
        boolean isValid = ControlInputTool.checkObject(invalidObject, TestObject.class);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isValidNumberSuccessfully() {
        // Arrange
        Number validNumber = 10;
        // Act
        boolean isValid = ControlInputTool.checkNumber(validNumber);
        // Assert
        assertTrue(isValid);
    }

    @Test
    public void isValidNumberFail() {
        // Arrange
        Number invalidNumber = -10;
        // Act
        boolean isValid = ControlInputTool.checkNumber(invalidNumber);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isValidTokensSuccessfully() {
        // Arrange
        Map<String, String> validTokens = new HashMap<>();
        validTokens.put("accessToken", "validAccessToken");
        validTokens.put("refreshToken", "validRefreshToken");
        // Act
        boolean isValid = ControlInputTool.checkToken(validTokens);
        // Assert
        assertTrue(isValid);
    }

    @Test
    public void isValidTokensFail() {
        // Arrange
        Map<String, String> invalidTokens = new HashMap<>();
        invalidTokens.put("accessToken", "validAccessToken");
        // Act
        boolean isValid = ControlInputTool.checkToken(invalidTokens);
        // Assert
        assertFalse(isValid);
    }

    @Test
    public void isValidRefreshTokenSuccessfully() {
        // Arrange
        Map<String, String> validRefreshToken = new HashMap<>();
        validRefreshToken.put("refreshToken", "validRefreshToken");
        // Act
        boolean isValid = ControlInputTool.checkRefreshToken(validRefreshToken);
        // Assert
        assertTrue(isValid);
    }

    @Test
    public void isValidRefreshTokenFail() {
        // Arrange
        Map<String, String> invalidRefreshToken = new HashMap<>();
        invalidRefreshToken.put("invalidKey", "validRefreshToken");
        // Act
        boolean isValid = ControlInputTool.checkRefreshToken(invalidRefreshToken);
        // Assert
        assertFalse(isValid);
    }

    // Classe de test interne pour isValidObject
    private static class TestObject {
        private String field;

        public TestObject(String field) {
            this.field = field;
        }

        public String getField() {
            return field;
        }

        public void setField(String field) {
            this.field = field;
        }
    }
}