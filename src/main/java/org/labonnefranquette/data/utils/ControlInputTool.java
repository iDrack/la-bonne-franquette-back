package org.labonnefranquette.data.utils;

import java.util.Map;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;

public class ControlInputTool {

    private static final PolicyFactory POLICY = Sanitizers.FORMATTING.and(Sanitizers.LINKS);
    private static final int MAX_LENGTH = 255;

    public static boolean isValidString(String input) {
        if (input == null || input.trim().isEmpty()) {
            return false;
        }
        if (input.length() > MAX_LENGTH) {
            return false;
        }
        String[] forbiddenPatterns = {
            "<script>", "</script>", "<?php", "?>", "public class", "import java",
            "javascript:", "onload=", "onerror=", "alert(", "document.cookie", "document.write"
        };
        for (String pattern : forbiddenPatterns) {
            if (input.toLowerCase().contains(pattern.toLowerCase())) {
                return false;
            }
        }
        String urlPattern = "^(http|https)://.*$";
        String urlWww = "^www.*$";
        if (input.matches(urlPattern) || input.matches(urlWww)) {
            return false;
        }
        String sanitizedInput = POLICY.sanitize(input);
        if (!sanitizedInput.equals(input)) {
            return false;
        }

        return true;
    }

    public static boolean isValidObject(Object obj, Class<?> classExpected) {
        if (obj == null || !classExpected.isInstance(obj)) {
            return false;
        }

        for (java.lang.reflect.Field field : classExpected.getDeclaredFields()) {
            if (field.getType().equals(String.class)) {
                field.setAccessible(true);
                try {
                    String value = (String) field.get(obj);
                    if (!isValidString(value)) {
                        return false;
                    }
                } catch (IllegalAccessException e) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isValidNumber(Number input) {
        if (input == null ) {
            return false;
        }
        try {
            return input.doubleValue() > 0;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isValidTokens(Map<String, String> tokens) {
        if (tokens == null || tokens.size() != 2) {
            return false;
        }
        if (!tokens.containsKey("accessToken") || !tokens.containsKey("refreshToken")) {
            return false;
        }
        if (!isValidString(tokens.get("accessToken")) || !isValidString(tokens.get("refreshToken"))) {
            return false;
        }
        return true;
    }

    public static boolean isValidRefreshToken(Map<String, String> refreshToken) {
        if (refreshToken == null || refreshToken.size() != 1) {
            return false;
        }
        if (!refreshToken.containsKey("refreshToken")) {
            return false;
        }
        if (!isValidString(refreshToken.get("refreshToken"))) {
            return false;
        }
        return true;
    }

    
}