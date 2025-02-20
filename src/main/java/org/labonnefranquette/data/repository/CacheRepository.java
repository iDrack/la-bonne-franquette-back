package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Cache;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CacheRepository extends JpaRepository<Cache, Long> {
    Cache getFirstById(long id);
}
