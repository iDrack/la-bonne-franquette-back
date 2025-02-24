package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Cache;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CacheRepository extends JpaRepository<Cache, Long> {
    Cache getFirstById(long id);

    @Query("SELECT c FROM Cache c WHERE c.restaurant.id = :id")
    Optional<Cache> getByRestaurantId(long id);
}
