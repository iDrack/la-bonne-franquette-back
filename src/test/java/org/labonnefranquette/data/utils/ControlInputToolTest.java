package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ControlInputToolTest {

    @Test
    void shouldAcceptValidString() {
        assertTrue(ControlInputTool.checkString("Une pizza 4 fromages"));
    }

    @Test
    void shouldRejectNullOrEmptyString() {
        assertFalse(ControlInputTool.checkString(null));
        assertFalse(ControlInputTool.checkString(""));
        assertFalse(ControlInputTool.checkString("   "));
    }

    @Test
    void shouldRejectStringTooLong() {
        String longString = "a".repeat(256);
        assertFalse(ControlInputTool.checkString(longString));
    }

    @Test
    void shouldRejectForbiddenPatterns() {
        assertFalse(ControlInputTool.checkString("<script>alert('xss')</script>"));
        assertFalse(ControlInputTool.checkString("<?php echo 'hack'; ?>"));
        assertFalse(ControlInputTool.checkString("javascript:alert('xss')"));
        assertFalse(ControlInputTool.checkString("onload=something"));
        assertFalse(ControlInputTool.checkString("import java.io.File;"));
        assertFalse(ControlInputTool.checkString("document.write('pwned')"));
    }

    @Test
    void shouldRejectUrls() {
        assertFalse(ControlInputTool.checkString("http://malicious.com"));
        assertFalse(ControlInputTool.checkString("https://malicious.com"));
        assertFalse(ControlInputTool.checkString("www.bad.com"));
    }

    @Test
    void shouldAcceptPositiveNumber() {
        assertTrue(ControlInputTool.checkNumber(42));
        assertTrue(ControlInputTool.checkNumber(0.5));
    }

    @Test
    void shouldRejectNullOrNonPositiveNumber() {
        assertFalse(ControlInputTool.checkNumber(null));
        assertFalse(ControlInputTool.checkNumber(0));
        assertFalse(ControlInputTool.checkNumber(-1));
    }

    @Test
    void shouldAcceptValidTokenMap() {
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", "abcd1234");
        tokens.put("refreshToken", "efgh5678");
        assertTrue(ControlInputTool.checkToken(tokens));
    }

    @Test
    void shouldRejectInvalidTokenMap() {
        assertFalse(ControlInputTool.checkToken(null));
        Map<String, String> tokens = new HashMap<>();
        tokens.put("accessToken", "abcd");
        assertFalse(ControlInputTool.checkToken(tokens));
        tokens.put("refreshToken", "<script>");
        assertFalse(ControlInputTool.checkToken(tokens));
    }

    @Test
    void shouldAcceptValidRefreshToken() {
        Map<String, String> refresh = new HashMap<>();
        refresh.put("refreshToken", "validToken");
        assertTrue(ControlInputTool.checkRefreshToken(refresh));
    }

    @Test
    void shouldRejectInvalidRefreshToken() {
        assertFalse(ControlInputTool.checkRefreshToken(null));
        Map<String, String> refresh = new HashMap<>();
        assertFalse(ControlInputTool.checkRefreshToken(refresh));
        refresh.put("refreshToken", "<script>");
        assertFalse(ControlInputTool.checkRefreshToken(refresh));
    }

    static class Dummy {
        public String field1;
        public String field2;
        public Dummy(String field1, String field2) {
            this.field1 = field1;
            this.field2 = field2;
        }
    }

    @Test
    void shouldAcceptValidObject() {
        Dummy d = new Dummy("pizza", "tacos");
        assertTrue(ControlInputTool.checkObject(d, Dummy.class));
    }

    @Test
    void shouldRejectObjectWithForbiddenString() {
        Dummy d = new Dummy("ok", "<script>");
        assertFalse(ControlInputTool.checkObject(d, Dummy.class));
    }
}
