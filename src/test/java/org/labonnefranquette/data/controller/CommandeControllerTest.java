package org.labonnefranquette.data.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.dto.impl.OrderCreateDTO;
import org.labonnefranquette.data.dto.impl.OrderReadDTO;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.services.OrderService;
import org.labonnefranquette.data.utils.DtoTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CommandeControllerTest {

    @Mock
    private OrderService commandeService;

    @Mock
    private DtoTools dtoTools;

    private static final String AUTH_TOKEN = "authToken";
    @Mock
    private SimpMessagingTemplate template;
    @InjectMocks
    private OrderController commandeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void fetchAllCommandesEnCoursWithValidStatus() {
        Order commande = new Order();
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        when(commandeService.getAllByStatus(OrderStatus.EN_COURS, AUTH_TOKEN))
                .thenReturn(List.of(commande));
        when(dtoTools.convertToDto(commande, OrderReadDTO.class))
                .thenReturn(commandeReadDTO);

        ResponseEntity<List<OrderReadDTO>> response =
                commandeController.fetchAllCommandesEnCours("EN_COURS", AUTH_TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
    }

    @Test
    void createCommandeWithValidData() {
        OrderCreateDTO commandeCreateDTO = new OrderCreateDTO();
        Order commande = new Order();
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        when(dtoTools.convertToEntity(commandeCreateDTO, Order.class))
                .thenReturn(commande);
        when(commandeService.create(commande, AUTH_TOKEN))
                .thenReturn(commande);
        when(dtoTools.convertToDto(commande, OrderReadDTO.class))
                .thenReturn(commandeReadDTO);

        ResponseEntity<?> response = commandeController.createCommande(commandeCreateDTO, AUTH_TOKEN);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(commandeReadDTO, response.getBody());
    }

    @Test
    void deleteCommandeWithValidId() {
        Long id = 1L;
        when(commandeService.delete(id)).thenReturn(true);

        ResponseEntity<Boolean> response = commandeController.deleteCommande(id, AUTH_TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(true, response.getBody());
    }

    @Test
    void updateCommandeWithValidId() {
        Long id = 1L;
        Order commande = new Order();
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        when(commandeService.updateStatus(id)).thenReturn(commande);
        when(dtoTools.convertToDto(commande, OrderReadDTO.class))
                .thenReturn(commandeReadDTO);

        ResponseEntity<OrderReadDTO> response = commandeController.updateCommande(id, AUTH_TOKEN);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(commandeReadDTO, response.getBody());
    }

    @Test
    void fetchAllCommandesEnCoursWithInvalidStatus() {
        ResponseEntity<List<OrderReadDTO>> response = commandeController.fetchAllCommandesEnCours("INVALID_STATUS", "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void fetchAllCommandesEnCoursWithNullStatus() {
        ResponseEntity<List<OrderReadDTO>> response = commandeController.fetchAllCommandesEnCours(null, "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void deleteCommandeWithInvalidId() {
        ResponseEntity<Boolean> response = commandeController.deleteCommande(-1L, "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void updateCommandeWithInvalidId() {
        ResponseEntity<OrderReadDTO> response = commandeController.updateCommande(-1L, "authToken");

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    void patchCommandeWithNullData() {
        Long id = 1L;

        ResponseEntity<OrderReadDTO> response = commandeController.patchCommande(id, null, AUTH_TOKEN);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
}