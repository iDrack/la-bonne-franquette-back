package org.labonnefranquette.data.model;

import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SelectionTest {

    @Test
    void settersAndGettersWork() {
        // Given
        Selection selection = new Selection();

        // When
        selection.setName("Menu Best Of");
        selection.setQuantity(2);
        selection.setTotalPrice(1800);
        selection.setModified(true);

        Article art1 = new Article();
        art1.setName("Burger");
        Article art2 = new Article();
        art2.setName("Frites");

        selection.setArticles(List.of(art1, art2));

        // Then
        assertThat(selection.getName()).isEqualTo("Menu Best Of");
        assertThat(selection.getQuantity()).isEqualTo(2);
        assertThat(selection.getTotalPrice()).isEqualTo(1800);
        assertThat(selection.isModified()).isTrue();

        assertThat(selection.getArticles()).hasSize(2);
        assertThat(selection.getArticles()).extracting(Article::getName)
                .containsExactlyInAnyOrder("Burger", "Frites");
    }

    @Test
    void defaultValuesWork() {
        // Given
        Selection selection = new Selection();

        // Then
        assertThat(selection.getName()).isNull();
        assertThat(selection.getQuantity()).isNull();
        assertThat(selection.getTotalPrice()).isEqualTo(0);
        assertThat(selection.getArticles()).isNull();
        assertThat(selection.isModified()).isFalse();
    }

    @Test
    void testEqualsAndHashCode() {
        Selection s1 = new Selection();
        s1.setName("Formule Midi");
        s1.setQuantity(1);

        Selection s2 = new Selection();
        s2.setName("Formule Midi");
        s2.setQuantity(1);

        assertThat(s1).isEqualTo(s2);
        assertThat(s1.hashCode()).isEqualTo(s2.hashCode());
    }
}
