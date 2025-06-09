package org.labonnefranquette.data.model;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.entity.Article;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("unit")
class ArticleTest {

    @Test
    void settersAndGettersWork() {
        // Given
        Article article = new Article();

        // When
        article.setName("Cheeseburger");
        article.setQuantity(3);
        article.setTotalPrice(2100);
        article.setModified(true);

        Ingredient ing1 = new Ingredient();
        ing1.setName("Salade");
        Ingredient ing2 = new Ingredient();
        ing2.setName("Steak");

        Addon addon1 = new Addon();
        addon1.setName("Cheddar");

        article.setIngredients(List.of(ing1, ing2));
        article.setAddons(List.of(addon1));

        // Then
        assertThat(article.getName()).isEqualTo("Cheeseburger");
        assertThat(article.getQuantity()).isEqualTo(3);
        assertThat(article.getTotalPrice()).isEqualTo(2100);
        assertThat(article.isModified()).isTrue();

        assertThat(article.getIngredients()).hasSize(2);
        assertThat(article.getIngredients()).extracting(Ingredient::getName).containsExactlyInAnyOrder("Salade", "Steak");

        assertThat(article.getAddons()).hasSize(1);
        assertThat(article.getAddons().stream().findFirst().get().getName()).isEqualTo("Cheddar");
    }

    @Test
    void defaultValuesWork() {
        // Given
        Article article = new Article();

        // Then
        assertThat(article.getName()).isNull();
        assertThat(article.getQuantity()).isNull();
        assertThat(article.getTotalPrice()).isEqualTo(0);
        assertThat(article.getIngredients()).isNull();
        assertThat(article.getAddons()).isNull();
        assertThat(article.isModified()).isFalse();
    }

    @Test
    void testEqualsAndHashCode() {
        Article a1 = new Article();
        a1.setName("Burger");
        a1.setQuantity(1);

        Article a2 = new Article();
        a2.setName("Burger");
        a2.setQuantity(1);

        assertThat(a1).isEqualTo(a2);
        assertThat(a1.hashCode()).isEqualTo(a2.hashCode());
    }
}
