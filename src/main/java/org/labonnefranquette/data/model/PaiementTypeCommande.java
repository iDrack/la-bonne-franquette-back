package org.labonnefranquette.data.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.labonnefranquette.data.model.interfaces.HasRestaurant;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaiementTypeCommande implements HasRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Le nom est obligatoire.")
    private String name;

    @Column(name = "is_enable", nullable = false)
    @NotNull(message = "La valeur de is_enable ne peut pas être null.")
    private boolean isEnable;

    @OneToMany(mappedBy = "type")
    @JsonIgnore
    private List<Paiement> paiements;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    @JsonIgnore
    private Restaurant restaurant;

    public PaiementTypeCommande(long id, String name, boolean isEnable, ArrayList<Paiement> paiements) {
        this.id = id;
        this.name = name;
        this.isEnable = isEnable;
        this.paiements = paiements;
    }
}
