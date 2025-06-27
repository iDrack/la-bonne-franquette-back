package org.labonnefranquette.data.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.labonnefranquette.data.dto.impl.PaymentCreateDTO;
import org.labonnefranquette.data.dto.impl.PaymentReadDTO;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.PaymentType;
import org.labonnefranquette.data.services.MailService;
import org.labonnefranquette.data.services.PaymentService;
import org.labonnefranquette.data.utils.ControlInputTool;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequestMapping("api/v1/payment")
@Tag(name = "Payment Controller", description = "Controller pour les interactions des paiements.")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    @Autowired
    private DtoTools dtoTools;
    @Autowired
    private MailService mailService;

    /**
     * Génère un PDF de la facture pour un paiement donné.
     *
     * @param id        identifiant du paiement
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return message de confirmation, HTTP 200 ou HTTP 500 en cas d'erreur
     */
    @Operation(
            summary = "Générer PDF de facture",
            description = "Crée un document PDF de la facture associée au paiement spécifié."
    )
    @GetMapping("pdf/generate/{id}")
    public ResponseEntity<String> generatePDF(@PathVariable("id") Long id,
                                              @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                              @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        try {
            Payment payment = paymentService.getById(id);

            paymentService.generatePDF(payment);
            return new ResponseEntity<>("PDF généré avec succés.", HttpStatus.OK);
        } catch (IOException e) {
            log.error("Erreur: ", e);
            return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Envoie la facture par e-mail à l'adresse fournie.
     *
     * @param id        identifiant du paiement
     * @param email     adresse e-mail du destinataire
     * @param details   true pour inclure le détail des items, false sinon
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return message de confirmation, HTTP 200 ou HTTP 400/500 selon le cas
     */
    @Operation(
            summary = "Envoyer facture par e-mail",
            description = "Envoie au client la facture du paiement sous forme de pièce jointe par e-mail."
    )
    @GetMapping("/send-receipt/{id}")
    public ResponseEntity<String> sendReceipt(@PathVariable("id") Long id, @RequestBody String email, @RequestBody boolean details,
                                              @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                              @RequestHeader(value = "Auth-Token", required = true) String authToken) {
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            try {
                Payment payment = paymentService.getById(id);
                mailService.sendMailReceipt(email, payment, details);
            } catch (IOException | MessagingException e) {
                log.error("Erreur: ", e);
                return new ResponseEntity<>(e.toString(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
            return new ResponseEntity<>("Facture envoyée.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("L'e-mail est invalide.", HttpStatus.METHOD_NOT_ALLOWED);
        }
    }

    /**
     * Renvoie la liste des types de paiement disponibles.
     *
     * @param authToken JWT pour l'authentification (obligatoire)
     * @return liste des PaiementType, HTTP 200 ou HTTP 404 si vide
     */
    @Operation(
            summary = "Lister les types de paiement",
            description = "Récupère tous les types de paiement gérés par l'application."
    )
    @GetMapping("/type")
    public ResponseEntity<List<PaymentType>> getAllByType(
            @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
            @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        List<PaymentType> listPaymentType = paymentService.getAllPaymentType();
        return new ResponseEntity<>(listPaymentType, HttpStatus.OK);
    }

    /**
     * Crée un nouveau paiement pour une commande donnée.
     *
     * @param paymentCreateDto données du paiement
     * @param orderId          identifiant de la commande associée
     * @param authToken        JWT pour l'authentification (obligatoire)
     * @return PaiementReadDTO du paiement créé, HTTP 200 ou HTTP 400 si erreur
     */
    @Operation(
            summary = "Créer un paiement",
            description = "Enregistre un paiement pour la commande spécifiée et renvoie ses détails."
    )
    @PostMapping("/{orderId}")
    public ResponseEntity<PaymentReadDTO> create(@RequestBody PaymentCreateDTO paymentCreateDto, @PathVariable Long orderId,
                                                 @Parameter(in = ParameterIn.HEADER, description = "Auth Token", schema = @Schema(type = "string"))
                                                             @RequestHeader(value = "Auth-Token", required = false) String authToken) {
        Payment payment;
        if (!ControlInputTool.checkObject(paymentCreateDto, PaymentCreateDTO.class)) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        try {
            payment = paymentService.create(orderId, dtoTools.convertToEntity(paymentCreateDto, Payment.class));
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(dtoTools.convertToDto(payment, PaymentReadDTO.class), HttpStatus.OK);
    }
}
