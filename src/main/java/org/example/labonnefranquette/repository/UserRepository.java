package org.example.labonnefranquette.repository;

import org.example.labonnefranquette.model.Categorie;
import org.example.labonnefranquette.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Byte> {


    Boolean existsByUsername(String username);
    User findByUsername(String username);

}
