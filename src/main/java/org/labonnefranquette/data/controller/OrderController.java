package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.OrderCreateDTO;
import org.labonnefranquette.data.dto.impl.OrderReadDTO;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.enums.OrderStatus;
import org.labonnefranquette.data.projection.OrderListProjection;
import org.labonnefranquette.data.services.OrderService;
import org.labonnefranquette.data.utils.ControlInputTool;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/order")
@Validated
@Tag(name = "Order Controller", description = "Controller pour les interactions des commandes.")
public class OrderController {

    private final SimpMessagingTemplate template;

    @Autowired
    public OrderController(SimpMessagingTemplate template) {
        this.template = template;
    }

    @Autowired
    private OrderService orderService;
    @Autowired
    private DtoTools dtoTools;

    /**
     * Récupère la liste des commandes filtrées par statut pour l'affichage cuisine.
     *
     * @param status    statut des commandes (ex : EN_ATTENTE, EN_COURS, TERMINE)
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return la liste des commandes au statut demandé, HTTP 200 ou HTTP 400 si statut invalide
     */
    @Operation(
            summary = "Récupérer les commandes par statut",
            description = "Renvoie la liste des commandes dont le statut correspond au paramètre pour l'écran cuisine."
    )
    @GetMapping(value = "/status/{status}", produces = "application/json")
    public ResponseEntity<List<OrderReadDTO>> getAllByStatus(@PathVariable String status,
                                                             @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                                          @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!ControlInputTool.checkString(status)) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            OrderStatus orderStatus = OrderStatus.valueOf(status.replace("-", "_").toUpperCase());
            List<Order> commandes = orderService.getAllByStatus(orderStatus, authToken);
            List<OrderReadDTO> commandesDtos = commandes.stream().map(commande -> dtoTools.convertToDto(commande, OrderReadDTO.class)).toList();
            return new ResponseEntity<>(commandesDtos, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @Operation(
            summary = "Récupérer toutes les commandes",
            description = "Renvoie la liste des commandes du restaurant correspondant à celui de l'utilisateur faisant la requête."
    )
    @GetMapping
    public ResponseEntity<List<OrderReadDTO>> fetchAllCommandes() {
        List<Order> commandes = orderService.findAllOrder();
        List<OrderReadDTO> commandesDtos = commandes.stream().map(commande -> dtoTools.convertToDto(commande, OrderReadDTO.class)).toList();
        return new ResponseEntity<>(commandesDtos, HttpStatus.OK);
    }

    /**
     * Récupère la liste des commandes sous forme de projection pour une date donnée.
     *
     * @param day       jour (1–31)
     * @param month     mois (1–12)
     * @param year      année (ex : 2025)
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return la liste des projections de commandes, HTTP 200 ou HTTP 500 en cas d'erreur serveur
     */
    @Operation(
            summary = "Récupérer la liste des commandes par date",
            description = "Renvoie les projections des commandes pour une date précise (jour-mois-année)."
    )
    @GetMapping("/list/{year}-{month}-{day}")
    public ResponseEntity<List<OrderListProjection>> getAllByDate(@PathVariable int day, @PathVariable int month, @PathVariable int year,
                                                                  @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                                                @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        List<OrderListProjection> orderList;
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month - 1, day, 0, 0, 0);
            Date date = calendar.getTime();
            orderList = orderService.getAllByDate(date);
        } catch (Exception e) {
            log.error("Erreur: ", e);
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(orderList, HttpStatus.OK);
    }

    /**
     * Récupère une commande par son ID.
     *
     * @param id        identifiant de la commande
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return les détails de la commande, HTTP 200 ou HTTP 404 si non trouvée
     */
    @Operation(
            summary = "Récupérer une commande par ID",
            description = "Renvoie les détails d'une commande identifiée par son ID."
    )
    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDTO> get(@PathVariable Long id,
                                            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                             @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        Order order = null;
        try {
            order = orderService.getById(id);
        } catch (NullPointerException e) {
            log.error("Erreur: ", e);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(dtoTools.convertToDto(order, OrderReadDTO.class), HttpStatus.OK);
    }

    /**
     * Crée une nouvelle commande et envoie une notification via WebSocket.
     *
     * @param orderCreateDto données de création de la commande
     * @param authToken   JWT pour l'authentification (obligatoire)
     * @return la commande créée sous forme DTO, HTTP 201 ou HTTP 400 si données invalides/prix incorrect
     */
    @Operation(
            summary = "Créer une commande",
            description = "Crée une nouvelle commande et notifie les clients via WebSocket."
    )
    @PostMapping(produces = "application/json")
    public ResponseEntity<?> create(@Valid @RequestBody OrderCreateDTO orderCreateDto,
                                            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                            @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            if (!ControlInputTool.checkObject(orderCreateDto, OrderCreateDTO.class)) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            Order order = orderService.create(dtoTools.convertToEntity(orderCreateDto, Order.class), authToken);
            OrderReadDTO orderReadDto = dtoTools.convertToDto(order, OrderReadDTO.class);
            this.template.convertAndSend("/socket/order", orderReadDto);
            return new ResponseEntity<>(orderReadDto, HttpStatus.CREATED);
        } catch (PriceException priceException) {
            return new ResponseEntity<>(priceException.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    /**
     * Supprime une commande par son ID.
     *
     * @param id        identifiant de la commande
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return true si suppression réussie, HTTP 200 ou HTTP 400 si ID invalide
     */
    @Operation(
            summary = "Supprimer une commande",
            description = "Supprime la commande identifiée par son ID."
    )
    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Boolean> delete(@PathVariable Long id,
                                                  @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                  @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        if (!ControlInputTool.checkNumber(id)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(orderService.delete(id), HttpStatus.OK);
    }

    /**
     * Fait passer une commande à l'étape suivante de son statut.
     *
     * @param id        identifiant de la commande
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return la commande mise à jour sous forme DTO, HTTP 200 ou HTTP 400 si ID invalide
     */
    @Operation(
            summary = "Avancer le statut d'une commande",
            description = "Fait progresser la commande à l'étape suivante de son cycle de vie."
    )
    @PutMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<OrderReadDTO> update(@PathVariable long id,
                                               @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                          @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        if (!ControlInputTool.checkNumber(id)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Order order = orderService.updateStatus(id);
        return new ResponseEntity<>(dtoTools.convertToDto(order, OrderReadDTO.class), HttpStatus.OK);
    }
    /**
     * Applique des modifications partielles sur une commande.
     *
     * @param id        identifiant de la commande
     * @param updates   map des champs et valeurs à mettre à jour
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return la commande mise à jour sous forme DTO, HTTP 200, HTTP 404 si non trouvée ou HTTP 400 si données invalides
     */
    @Operation(
            summary = "Mettre à jour partiellement une commande",
            description = "Applique des modifications partielles sur la commande spécifiée."
    )
    @PatchMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<OrderReadDTO> patch(@PathVariable long id, @RequestBody Map<String, Object> updates,
                                              @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                         @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        if (id <= 0 || updates == null) {
            log.error("Erreur : Données invalide ou nuls.");
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        Order updatedOrder = orderService.patch(id, updates);
        if (updatedOrder == null) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(dtoTools.convertToDto(updatedOrder, OrderReadDTO.class), HttpStatus.OK);
    }
}
