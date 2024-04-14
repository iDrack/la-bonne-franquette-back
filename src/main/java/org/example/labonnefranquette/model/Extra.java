package org.example.labonnefranquette.model;


import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(schema = "lbf-extras")
public class Extra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "tauxtva_id", nullable = false)
    private TauxTVA tauxTVA;

    @Column(name = "prixHT", nullable = false)
    private int prixHT;

}
