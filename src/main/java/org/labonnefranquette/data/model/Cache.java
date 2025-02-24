package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import lombok.Data;
import org.labonnefranquette.data.model.interfaces.HasRestaurant;

@Entity
@Table(name = "cache")
@Data
public class Cache implements HasRestaurant {
    @Column(name = "version", nullable = false, length = 50)
    int version;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "restaurant_id", referencedColumnName = "id")
    private Restaurant restaurant;
}
