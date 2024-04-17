package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Set;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Byte id;

    @Column(length = 150, nullable = false)
    private String email;

    @Column(length = 255, nullable = false)
    private String password;

    @Column(length = 50)
    @ElementCollection(targetClass = Roles.class)
    @Enumerated(EnumType.STRING)
    private Set<Roles> roles;

    @Override
    public String toString() {
      return "user="+this.email+".";
    }
}
