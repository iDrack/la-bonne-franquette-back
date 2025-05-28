package org.labonnefranquette.data.utils;

import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.OrderCreateDTO;
import org.labonnefranquette.data.dto.impl.OrderReadDTO;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.enums.OrderStatus;

import java.util.ArrayList;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

class DtoToolsTest {

    @Test
    void shouldMapOrderToOrderReadDTO() {
        Order order = new Order();
        order.setId(42L);
        order.setNumber((short) 123);
        order.setCreationDate(new Date(1700000000000L));
        order.setDeliveryDate(new Date(1700003600000L));
        order.setStatus(OrderStatus.EN_COURS);
        order.setDineIn(true);
        order.setTotalItems(3);
        order.setTotalPrice(2500);
        order.setArticles(new ArrayList<>());
        order.setMenus(new ArrayList<>());
        order.setPayments(new ArrayList<>());
        order.setPaymentType("CB");
        order.setPaid(true);

        DtoTools dtoTools = new DtoTools();
        OrderReadDTO dto = dtoTools.convertToDto(order, OrderReadDTO.class);

        assertNotNull(dto);
        assertEquals(order.getId(), dto.getId());
        assertEquals(order.getNumber(), dto.getNumber());
        assertEquals(order.getCreationDate(), dto.getCreationDate());
        assertEquals(order.getDeliveryDate(), dto.getDeliveryDate());
        assertEquals(order.getStatus(), dto.getStatus());
        assertEquals(order.getDineIn(), dto.getDineIn());
        assertEquals(order.getTotalItems(), dto.getTotalItems());
        assertEquals(order.getTotalPrice(), dto.getTotalPrice());
        assertEquals(order.getArticles(), dto.getArticles());
        assertEquals(order.getMenus(), dto.getMenus());
        assertEquals(order.getPayments(), dto.getPayments());
        assertEquals(order.getPaymentType(), dto.getPaymentType());
        assertEquals(order.isPaid(), dto.isPaid());
    }

    @Test
    void shouldMapOrderCreateDTOToOrder() {
        OrderCreateDTO createDTO = new OrderCreateDTO(
                true,
                new Date(1700000000000L),
                new Date(1700003600000L),
                OrderStatus.EN_COURS,
                4500,
                new ArrayList<>(),
                new ArrayList<>()
        );

        DtoTools dtoTools = new DtoTools();
        Order order = dtoTools.convertToEntity(createDTO, Order.class);

        assertNotNull(order);
        assertEquals(createDTO.getDineIn(), order.getDineIn());
        assertEquals(createDTO.getCreationDate(), order.getCreationDate());
        assertEquals(createDTO.getDeliveryDate(), order.getDeliveryDate());
        assertEquals(createDTO.getStatus(), order.getStatus());
        assertEquals(createDTO.getTotalPrice(), order.getTotalPrice());
        assertEquals(createDTO.getArticles(), order.getArticles());
        assertEquals(createDTO.getMenus(), order.getMenus());
    }

    @Test
    void shouldHandleIllegalInputs() {
        DtoTools dtoTools = new DtoTools();
        assertThrows(IllegalArgumentException.class, () -> dtoTools.convertToDto(null, OrderReadDTO.class));
        assertThrows(IllegalArgumentException.class, () -> dtoTools.convertToEntity(null, Order.class));
    }

    @Test
    void shouldHandleEmptyFields() {
        Order order = new Order();
        DtoTools dtoTools = new DtoTools();
        OrderReadDTO dto = dtoTools.convertToDto(order, OrderReadDTO.class);

        assertNotNull(dto);
        assertEquals(0L, dto.getId());
        assertNull(dto.getCreationDate());
        assertNull(dto.getDeliveryDate());
    }
}
