package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.CommandeCreateDTO;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.utils.DtoTools;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ActiveProfiles("test")
public class CommandeCreateDTOTest {

    private Boolean surPlace;
    private Collection<Selection> menus;
    private Collection<Paiement> paiementSet;
    private StatusCommande status;
    private Collection<Article> articles;
    private int prixHT;
    private DtoTools dtoTools;
    private CommandeCreateDTO commandeCreateDTO;

    @BeforeEach
    public void setUp() {
        surPlace = true;
        menus = new ArrayList<>();
        paiementSet = new ArrayList<>();
        status = StatusCommande.EN_COURS;
        articles = new ArrayList<>();
        prixHT = 100;
        dtoTools = new DtoTools();
        commandeCreateDTO = new CommandeCreateDTO(surPlace, new Date(), new Date(), status, prixHT, articles, menus);
    }

    @Test
    public void testCommandeCreateDTONotNull() {
        assertNotNull(commandeCreateDTO);
    }

    @Test
    public void testSurPlace() {
        assertEquals(surPlace, commandeCreateDTO.getSurPlace());
    }

    @Test
    public void testMenus() {
        assertEquals(menus, commandeCreateDTO.getMenus());
    }

    @Test
    public void testStatus() {
        assertEquals(status, commandeCreateDTO.getStatus());
    }

    @Test
    public void testArticles() {
        assertEquals(articles, commandeCreateDTO.getArticles());
    }

    @Test
    public void testPrixTTC() {
        assertEquals(prixHT, commandeCreateDTO.getPrixTTC());
    }

    @Test
    public void testConvertedCommandeNotNull() {
        Commande commande = dtoTools.convertToEntity(commandeCreateDTO, Commande.class);
        assertNotNull(commande);
    }

    @Test
    public void testConvertedSurPlace() {
        Commande commande = dtoTools.convertToEntity(commandeCreateDTO, Commande.class);
        assertEquals(surPlace, commande.getSurPlace());
    }

    @Test
    public void testConvertedMenus() {
        Commande commande = dtoTools.convertToEntity(commandeCreateDTO, Commande.class);
        assertEquals(menus, commande.getMenus());
    }

    @Test
    public void testConvertedPaiementSet() {
        Commande commande = dtoTools.convertToEntity(commandeCreateDTO, Commande.class);
        assertEquals(paiementSet, commande.getPaiementSet());
    }

    @Test
    public void testConvertedStatus() {
        Commande commande = dtoTools.convertToEntity(commandeCreateDTO, Commande.class);
        assertEquals(status, commande.getStatus());
    }

    @Test
    public void testConvertedArticles() {
        Commande commande = dtoTools.convertToEntity(commandeCreateDTO, Commande.class);
        assertEquals(articles, commande.getArticles());
    }

    @Test
    public void testConvertedPrixTTC() {
        Commande commande = dtoTools.convertToEntity(commandeCreateDTO, Commande.class);
        assertEquals(prixHT, commande.getPrixTTC());
    }
}
