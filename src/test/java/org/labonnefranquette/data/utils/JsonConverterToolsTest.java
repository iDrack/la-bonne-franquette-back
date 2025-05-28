package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class JsonConverterToolsTest {

    private final JsonConverterTools converter = new JsonConverterTools();

    @Test
    void shouldSerializeAndDeserializeArticles() {
        Ingredient ing = new Ingredient();
        ing.setId(1L);
        ing.setName("Mozza");

        Addon addon = new Addon();
        addon.setId(2L);
        addon.setName("Fromage");

        Article art = new Article();
        art.setName("Pizza");
        art.setQuantity(2);
        art.setTotalPrice(1500);
        art.setIngredients(List.of(ing));
        art.setAddons(List.of(addon));
        art.setModified(false);

        Collection<Article> articles = List.of(art);

        String json = converter.convertToDatabaseColumn((Collection<Object>) (Collection<?>) articles);
        assertNotNull(json);
        assertTrue(json.contains("Pizza"));
        assertTrue(json.contains("Mozza"));
        assertTrue(json.contains("Fromage"));

        Collection<Object> deserialized = converter.convertToEntityAttribute(json);
        assertNotNull(deserialized);
        assertEquals(1, deserialized.size());
        Object first = deserialized.iterator().next();
        assertInstanceOf(LinkedHashMap.class, first);
        assertEquals("Pizza", ((java.util.Map<?,?>) first).get("name"));
        assertEquals(2, ((java.util.Map<?,?>) first).get("quantity"));
    }

    @Test
    void shouldSerializeAndDeserializeSelections() {
        Article art = new Article();
        art.setName("Tacos");
        art.setQuantity(1);
        art.setTotalPrice(800);

        Selection sel = new Selection();
        sel.setName("Menu étudiant");
        sel.setArticles(List.of(art));
        sel.setQuantity(1);
        sel.setTotalPrice(1000);
        sel.setModified(false);

        Collection<Selection> selections = List.of(sel);

        String json = converter.convertToDatabaseColumn((Collection<Object>) (Collection<?>) selections);
        assertNotNull(json);
        assertTrue(json.contains("Menu étudiant"));

        Collection<Object> deserialized = converter.convertToEntityAttribute(json);
        assertNotNull(deserialized);
        assertEquals(1, deserialized.size());
        Object first = deserialized.iterator().next();
        assertInstanceOf(LinkedHashMap.class, first);
        assertEquals("Menu étudiant", ((java.util.Map<?,?>) first).get("name"));
    }

    @Test
    void shouldHandleEmptyCollection() {
        Collection<Article> articles = new ArrayList<>();
        String json = converter.convertToDatabaseColumn((Collection<Object>) (Collection<?>) articles);
        assertNotNull(json);
        assertEquals("[]", json);

        Collection<Object> deserialized = converter.convertToEntityAttribute(json);
        assertNotNull(deserialized);
        assertEquals(0, deserialized.size());
    }

    @Test
    void shouldThrowExceptionOnInvalidJson() {
        assertThrows(RuntimeException.class, () -> converter.convertToEntityAttribute("not a json"));
    }
}
