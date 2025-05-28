package org.labonnefranquette.data.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.Restaurant;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.projection.OrderListProjection;
import org.labonnefranquette.data.repository.OrderRepository;
import org.labonnefranquette.data.services.RestaurantService;
import org.labonnefranquette.data.services.impl.OrderServiceImpl;
import org.labonnefranquette.data.utils.OrderTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderTools orderTools;

    @Mock
    private RestaurantService restaurantService;

    @Mock
    private org.labonnefranquette.data.security.JWTUtil jwtUtil;

    @InjectMocks
    private OrderServiceImpl orderService;

    private final String token = "dummy-token";
    private Restaurant restaurant;
    private Order order;

    @BeforeEach
    void setUp() {
        restaurant = new Restaurant();
        restaurant.setId(5L);

        order = new Order();
        order.setId(1L);
        order.setArticles(Collections.emptyList());
        order.setMenus(Collections.emptyList());
        order.setPayments(Collections.emptyList());
        order.setStatus(OrderStatus.EN_COURS);
    }

    @Test
    void getAllByStatus_ShouldFilterByRestaurantId() {
        Order o1 = new Order(); o1.setRestaurant(restaurant);
        Order o2 = new Order(); o2.setRestaurant(new Restaurant());
        when(jwtUtil.extractRestaurantId(token)).thenReturn(5L);
        when(orderRepository.findAllByStatus(OrderStatus.EN_COURS))
                .thenReturn(List.of(o1, o2));

        List<Order> result = orderService.getAllByStatus(OrderStatus.EN_COURS, token);

        assertEquals(1, result.size());
        assertSame(o1, result.get(0));
        verify(jwtUtil).extractRestaurantId(token);
        verify(orderRepository).findAllByStatus(OrderStatus.EN_COURS);
    }

    @Test
    void findAllOrder_ShouldDelegateToRepository() {
        List<Order> orders = List.of(order);
        when(orderRepository.findAll()).thenReturn(orders);

        List<Order> result = orderService.findAllOrder();

        assertSame(orders, result);
        verify(orderRepository).findAll();
    }

    @Test
    void getById_WhenExists_ShouldReturnOrder() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.getById(1L);

        assertSame(order, result);
        verify(orderRepository).findById(1L);
    }

    @Test
    void getById_WhenNotExists_ShouldThrow() {
        when(orderRepository.findById(2L)).thenReturn(Optional.empty());
        NullPointerException ex = assertThrows(NullPointerException.class,
                () -> orderService.getById(2L));
        assertEquals("Commande n'existe pas.", ex.getMessage());
    }

    @Test
    void create_WhenPriceInvalid_ShouldThrowPriceException() {
        when(jwtUtil.extractRestaurantId(token)).thenReturn(5L);
        when(restaurantService.findAllById(5L)).thenReturn(Optional.of(restaurant));
        when(orderTools.checkPrice(order)).thenReturn(false);

        PriceException ex = assertThrows(PriceException.class,
                () -> orderService.create(order, token));
        assertEquals("Le prix saisie est incorrect", ex.getMessage());
    }

    @Test
    void create_WhenValid_ShouldPopulateAndSave() {
        when(jwtUtil.extractRestaurantId(token)).thenReturn(5L);
        when(restaurantService.findAllById(5L)).thenReturn(Optional.of(restaurant));
        when(orderTools.checkPrice(order)).thenReturn(true);
        when(orderTools.setOrderPaymentType(order.getPayments())).thenReturn("AUCUN");
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.create(order, token);

        assertSame(order, result);
        assertSame(restaurant, result.getRestaurant());
        assertEquals(0, result.getTotalItems());
        assertEquals("AUCUN", result.getPaymentType());
        verify(orderRepository).save(order);
    }

    @Test
    void delete_WhenNotExists_ShouldReturnFalse() {
        when(orderRepository.findById(1L)).thenReturn(Optional.empty());
        assertFalse(orderService.delete(1L));
    }

    @Test
    void delete_WhenExists_ShouldDeleteAndReturnTrue() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        boolean result = orderService.delete(1L);
        assertTrue(result);
        verify(orderRepository).deleteById(1L);
    }

    @Test
    void addPayment_ShouldAddAndSave() {
        Payment payment = new Payment();
        order.setPayments(null);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderTools.setOrderPaymentType(anyList())).thenReturn("CB");
        when(orderRepository.save(any(Order.class))).thenAnswer(a -> a.getArgument(0));

        Order result = orderService.addPayment(order, payment);

        assertEquals(1, result.getPayments().size());
        assertEquals("CB", result.getPaymentType());
        verify(orderRepository).save(order);
    }

    @Test
    void updateStatus_WhenInProgress_ShouldUpdateAndSave() {
        order.setStatus(OrderStatus.EN_COURS);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Order result = orderService.updateStatus(1L);

        assertEquals(OrderStatus.TERMINEE, result.getStatus());
        verify(orderRepository).save(order);
    }

    @Test
    void updateStatus_WhenNotInProgress_ShouldReturnUnchanged() {
        order.setStatus(OrderStatus.TERMINEE);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Order result = orderService.updateStatus(1L);

        assertSame(order, result);
        verify(orderRepository, never()).save(any());
    }

    @Test
    void patch_ShouldUpdateFieldsAndSave() {
        Date now = new Date();
        order.setStatus(OrderStatus.EN_COURS);
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(order);

        Map<String, Object> updates = new HashMap<>();
        updates.put("status", "TERMINEE");
        updates.put("totalItems", 5);
        String dateStr = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS").format(now);
        updates.put("creationDate", dateStr);

        Order result = orderService.patch(1L, updates);

        assertEquals(OrderStatus.TERMINEE, result.getStatus());
        assertEquals(5, result.getTotalItems());
        assertNotNull(result.getCreationDate());
        verify(orderRepository).save(order);
    }

    @Test
    void patch_WhenInvalidDateFormat_ShouldThrow() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));
        Map<String, Object> updates = Map.of("creationDate", "bad-format");

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> orderService.patch(1L, updates));
        assertTrue(ex.getMessage().contains("Invalid date format"));
    }

    @Test
    void getAllByDate_ShouldDelegateToRepository() {
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        OrderListProjection proj = mock(OrderListProjection.class);
        when(orderRepository.findAllByDate(sqlDate)).thenReturn(List.of(proj));

        List<OrderListProjection> result = orderService.getAllByDate(new Date(sqlDate.getTime()));

        assertEquals(1, result.size());
        assertSame(proj, result.get(0));
        verify(orderRepository).findAllByDate(sqlDate);
    }
}
