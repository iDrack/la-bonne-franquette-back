package org.labonnefranquette.data.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.labonnefranquette.data.model.enums.Roles;
import org.labonnefranquette.data.model.interfaces.HasRestaurant;

import java.util.Date;

@Entity
@Table(name = "users", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"email"})
})
@Data
public class User implements HasRestaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Byte id;

    @Column(name = "username", length = 100, nullable = false, unique = true)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String username;

    @Column(name = "password", length = 255, nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String password;

    @Column(name = "last_connection", nullable = true)
    private Date lastConnection;

    @Column(name = "roles", length = 100, nullable = false)
    @NotNull(message = "Ce champs ne peut pas être vide")
    private String roles;

    @ManyToOne
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public void setRoles(Roles role) {
        if (this.roles == null) {
            this.roles = role.name();
        } else {
            this.roles += ", " + role.name();
        }
    }

    @Override
    public String toString() {
        return "user=" + this.username + ".";
    }
}
