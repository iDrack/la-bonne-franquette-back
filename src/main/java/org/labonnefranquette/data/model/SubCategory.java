package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@DiscriminatorValue("sub-category")
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory extends Category {

    @ManyToOne
    @JoinColumn(
            name = "category_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "FK_subcategory_parent",
                    foreignKeyDefinition = "FOREIGN KEY (category_id) REFERENCES categories(id) ON DELETE CASCADE"
            )
    )
    @JsonBackReference
    private Category category;

    @Transient
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private long categoryId;


    @PostLoad
    private void onLoad() {
        if (category != null) {
            this.categoryId = category.getId();
        }
    }
}
