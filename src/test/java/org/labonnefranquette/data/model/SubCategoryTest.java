package org.labonnefranquette.data.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

@Tag("unit")
class SubCategoryTest {

    @Test
    void shouldConstructSubCategoryAndAccessFields() {
        Category parent = new Category();
        SubCategory sub = new SubCategory(parent, 0L);

        assertSame(parent, sub.getCategory());
        assertEquals(0L, sub.getCategoryId());
    }

    @Test
    void shouldSetAndGetFields() {
        SubCategory sub = new SubCategory();
        Category parent = new Category();
        sub.setCategory(parent);
        sub.setCategoryId(12L);

        assertSame(parent, sub.getCategory());
        assertEquals(12L, sub.getCategoryId());
    }

    @Test
    void shouldRespectEqualsAndHashCode() {
        Category parent = new Category(1L, "Pizza", null, "category", null);
        SubCategory sc1 = new SubCategory(parent, 5L);
        SubCategory sc2 = new SubCategory(parent, 5L);
        SubCategory sc3 = new SubCategory(new Category(2L, "Dessert", null, "category", null), 10L);

        assertEquals(sc1, sc2);
        assertEquals(sc1.hashCode(), sc2.hashCode());
        assertNotEquals(sc1, sc3);
        assertNotEquals(sc1.hashCode(), sc3.hashCode());
    }

    @Test
    void shouldHaveToStringWithParentCategory() {
        Category parent = new Category(1L, "Tartes", null, "category", null);
        SubCategory sub = new SubCategory(parent, 22L);
        String str = sub.toString();
        assertTrue(str.contains("SubCategory"));
    }

    @Test
    void shouldSerializeSubCategoryToJson() throws JsonProcessingException {
        Category parent = new Category();
        parent.setId(3L);
        SubCategory sub = new SubCategory(parent, 0L);
        sub.onLoad();

        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(sub);

        assertTrue(json.contains("\"categoryId\":3"));
        assertFalse(json.contains("\"category\""));
    }

    @Test
    void shouldSetCategoryIdOnLoadIfParentExists() {
        Category parent = new Category();
        parent.setId(42L);
        SubCategory sub = new SubCategory();
        sub.setCategory(parent);
        sub.onLoad();
        assertEquals(42L, sub.getCategoryId());
    }
}
