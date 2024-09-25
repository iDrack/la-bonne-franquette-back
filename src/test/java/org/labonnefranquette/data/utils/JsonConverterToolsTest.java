package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class JsonConverterToolsTest {

    private JsonConverterTools jsonConverterTools;

    @BeforeEach
    public void setup() {
        jsonConverterTools = new JsonConverterTools();
    }

    @Test
    public void convertToDatabaseColumn_returnsJsonString() {
        Collection<Object> data = Arrays.asList("element1", "element2");
        String expectedJson = "[\"element1\",\"element2\"]";
        assertEquals(expectedJson, jsonConverterTools.convertToDatabaseColumn(data));
    }

    @Test
    public void convertToDatabaseColumn_throwsExceptionOnInvalidData() {
        Collection<Object> data = Collections.singletonList(new Object());
        assertThrows(RuntimeException.class, () -> jsonConverterTools.convertToDatabaseColumn(data));
    }

    @Test
    public void convertToEntityAttribute_returnsCollection() {
        String json = "[\"element1\",\"element2\"]";
        Collection<Object> expectedData = Arrays.asList("element1", "element2");
        assertEquals(expectedData, jsonConverterTools.convertToEntityAttribute(json));
    }

    @Test
    public void convertToEntityAttribute_throwsExceptionOnInvalidJson() {
        String invalidJson = "invalid";
        assertThrows(RuntimeException.class, () -> jsonConverterTools.convertToEntityAttribute(invalidJson));
    }
}