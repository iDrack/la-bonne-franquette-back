package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType, Long> {
    PaymentType findByName(String name);
}