package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.PaiementCreateDTO;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.utils.DtoTools;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PaiementCreateDTOTest {

    private PaiementTypeCommande type;
    private int prixPaye;
    private Boolean ticketEnvoye;
    private PaiementCreateDTO paiementCreateDTO;
    private DtoTools dtoTools;

    @BeforeEach
    public void setUp() {
        type = PaiementTypeCommande.CB;
        prixPaye = 100;
        ticketEnvoye = true;
        paiementCreateDTO = new PaiementCreateDTO(type, prixPaye, ticketEnvoye);
        dtoTools = new DtoTools();
    }

    @Test
    public void testPaiementCreateDTONotNull() {
        assertNotNull(paiementCreateDTO);
    }

    @Test
    public void testType() {
        assertEquals(type, paiementCreateDTO.getType());
    }

    @Test
    public void testPrixPaye() {
        assertEquals(prixPaye, paiementCreateDTO.getPrixTTC());
    }

    @Test
    public void testTicketEnvoye() {
        assertEquals(ticketEnvoye, paiementCreateDTO.getTicketEnvoye());
    }

    @Test
    public void testConvertedPaiementNotNull() {
        Paiement paiement = dtoTools.convertToEntity(paiementCreateDTO, Paiement.class);
        assertNotNull(paiement);
    }

    @Test
    public void testConvertedType() {
        Paiement paiement = dtoTools.convertToEntity(paiementCreateDTO, Paiement.class);
        assertEquals(type, paiement.getType());
    }

    @Test
    public void testConvertedPrixPaye() {
        Paiement paiement = dtoTools.convertToEntity(paiementCreateDTO, Paiement.class);
        assertEquals(prixPaye, paiement.getPrixTTC());
    }

    @Test
    public void testConvertedTicketEnvoye() {
        Paiement paiement = dtoTools.convertToEntity(paiementCreateDTO, Paiement.class);
        assertEquals(ticketEnvoye, paiement.getTicketEnvoye());
    }
}