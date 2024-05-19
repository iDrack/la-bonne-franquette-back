package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Extra;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExtraRepository extends JpaRepository<Extra, Long> {
}
