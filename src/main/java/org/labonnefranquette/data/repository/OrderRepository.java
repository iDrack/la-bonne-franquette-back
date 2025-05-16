package org.labonnefranquette.data.repository;

import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.projection.OrderListProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT o.id as id, o.number as number, o.creationDate as creationDate, o.deliveryDate as deliveryDate, o.dineIn as dineIn, o.paid as paid, o.totalPrice as totalPrice, o.status as status, o.paymentType as paymentType FROM Order o WHERE DATE(o.creationDate) = :date ORDER BY o.creationDate DESC")
    List<OrderListProjection> findAllByDate(@Param("date") Date date);

    @Query(value = "SELECT o FROM Order o WHERE o.status = :status")
    List<Order> findAllByStatus(@Param("status") OrderStatus status);
}
