package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<List<Payment>> findByOrderId(Long orderId);
}
