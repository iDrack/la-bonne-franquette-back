package org.labonnefranquette.data.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;

@Getter
@Setter
@Entity
public class PaiementTypeCommandeEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private PaiementTypeCommande type;

    private boolean isEnable;
}