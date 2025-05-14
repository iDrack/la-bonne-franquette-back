package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.model.interfaces.HasRestaurantAbs;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentType extends HasRestaurantAbs {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    @NotNull(message = "Le nom est obligatoire.")
    private String name;

    @Column(name = "is_enable", nullable = false)
    @NotNull(message = "La valeur de is_enable ne peut pas être null.")
    @JsonProperty("isEnable")
    private boolean isEnable;

    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private List<Payment> payments;

    public PaymentType(long id, String name, boolean isEnable, ArrayList<Payment> paiements) {
        this.id = id;
        this.name = name;
        this.isEnable = isEnable;
        this.payments = paiements;
    }

    @JsonProperty("isEnable")
    public boolean getIsEnable() {
        return isEnable;
    }

    @JsonProperty("isEnable")
    public void setIsEnable(boolean isEnable) {
        this.isEnable = isEnable;
    }
}