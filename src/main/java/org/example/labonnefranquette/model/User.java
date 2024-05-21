package org.example.labonnefranquette.model;

import jakarta.persistence.*;
import lombok.Data;
import org.example.labonnefranquette.model.enums.Roles;

import java.lang.reflect.Array;
import java.util.*;

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

    @Column(name = "email", length = 100, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 255, nullable = false)
    private String password;

    @Column(name = "last_connection", nullable = true)
    private Date lastConnection;

    @Column(name = "roles", length = 100, nullable = false)
    private String roles;


    public void setRoles(Roles role) {
        if (this.roles == null) {
            this.roles = role.name();
        } else {
            this.roles += ", "+role.name();
        }
    }

    @Override
    public String toString() {
      return "user="+this.email+".";
    }
}
