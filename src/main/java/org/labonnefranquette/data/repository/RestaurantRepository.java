package org.labonnefranquette.data.repository;

import jakarta.validation.constraints.NotNull;
import org.labonnefranquette.data.model.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
    Optional<Restaurant> findById(Long id);

    Restaurant findByName(@NotNull(message = "Ce champs ne peut pas être vide") String name);

    Boolean existsByName(String username);
}
