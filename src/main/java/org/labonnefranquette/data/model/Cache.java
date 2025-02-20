package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cache")
@Data
public class Cache {
    @Column(name = "version", nullable = false, length = 50)
    int version;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
}
