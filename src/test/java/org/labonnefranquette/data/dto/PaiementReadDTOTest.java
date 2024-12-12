package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.PaiementReadDTO;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
public class PaiementReadDTOTest {

    private long id;
    private long commandeId;
    private Date date;
    private PaiementTypeCommande type;
    private Boolean ticketEnvoye;
    private int prixHT;
    private int prixTTC;
    private PaiementReadDTO paiementReadDTO;
    private DtoTools dtoTools;

    @BeforeEach
    public void setUp() {
        id = 1L;
        commandeId = 2L;
        date = new Date(System.currentTimeMillis());
        type = PaiementTypeCommande.CB;
        ticketEnvoye = true;
        prixHT = 100;
        prixTTC = 110;
        paiementReadDTO = new PaiementReadDTO();
        paiementReadDTO.setId(id);
        paiementReadDTO.setCommandeId(commandeId);
        paiementReadDTO.setDate(date);
        paiementReadDTO.setType(type);
        paiementReadDTO.setTicketEnvoye(ticketEnvoye);
        paiementReadDTO.setPrixHT(prixHT);
        paiementReadDTO.setPrixTTC(prixTTC);
        dtoTools = new DtoTools();
    }

    @Test
    public void testPaiementReadDTONotNull() {
        assertNotNull(paiementReadDTO);
    }

    @Test
    public void testId() {
        assertEquals(id, paiementReadDTO.getId());
    }

    @Test
    public void testCommandeId() {
        assertEquals(commandeId, paiementReadDTO.getCommandeId());
    }

    @Test
    public void testDate() {
        assertEquals(date, paiementReadDTO.getDate());
    }

    @Test
    public void testType() {
        assertEquals(type, paiementReadDTO.getType());
    }

    @Test
    public void testTicketEnvoye() {
        assertEquals(ticketEnvoye, paiementReadDTO.getTicketEnvoye());
    }

    @Test
    public void testPrixHT() {
        assertEquals(prixHT, paiementReadDTO.getPrixHT());
    }

    @Test
    public void testPrixTTC() {
        assertEquals(prixTTC, paiementReadDTO.getPrixTTC());
    }

    @Test
    public void testConvertedPaiementNotNull() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertNotNull(paiement);
    }

    @Test
    public void testConvertedId() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertEquals(id, paiement.getId());
    }

    @Test
    public void testConvertedCommandeId() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertEquals(commandeId, paiement.getCommande().getId());
    }

    @Test
    public void testConvertedDate() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertEquals(date, paiement.getDate());
    }

    @Test
    public void testConvertedType() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertEquals(type, paiement.getType());
    }

    @Test
    public void testConvertedTicketEnvoye() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertEquals(ticketEnvoye, paiement.getTicketEnvoye());
    }

    @Test
    public void testConvertedPrixHT() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertEquals(prixHT, paiement.getPrixHT());
    }

    @Test
    public void testConvertedPrixTTC() {
        Paiement paiement = dtoTools.convertToEntity(paiementReadDTO, Paiement.class);
        assertEquals(prixTTC, paiement.getPrixTTC());
    }
}

