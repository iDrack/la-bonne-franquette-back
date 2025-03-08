package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.CommandeReadDTO;
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
public class CommandeReadDTOTest {

    private long commandeId;
    private short numero;
    private Date dateSaisie;
    private Date dateLivraison;
    private StatusCommande status;
    private Boolean surPlace;
    private int nbArticle;
    private int prixTTC;
    private Collection<Article> articles;
    private Collection<Selection> menus;
    private Collection<Paiement> paiementSet;
    private String paiementTypeCommande;
    private DtoTools dtoTools;
    private Commande commande;

    @BeforeEach
    public void setUp() {
        commandeId = 1L;
        numero = 123;
        dateSaisie = new Date();
        dateLivraison = new Date();
        status = StatusCommande.EN_COURS;
        surPlace = true;
        nbArticle = 5;
        prixTTC = 100;
        articles = new ArrayList<>();
        menus = new ArrayList<>();
        paiementSet = new ArrayList<>();
        paiementTypeCommande = "CB";
        dtoTools = new DtoTools();
        commande = new Commande();
        commande.setId(commandeId);
        commande.setNumero(numero);
        commande.setDateSaisie(dateSaisie);
        commande.setDateLivraison(dateLivraison);
        commande.setStatus(status);
        commande.setSurPlace(surPlace);
        commande.setNbArticle(nbArticle);
        commande.setPrixTTC(prixTTC);
        commande.setArticles(articles);
        commande.setMenus(menus);
        commande.setPaiementSet(paiementSet);
        commande.setPaiementType("CB");
    }

    @Test
    public void testCommandeReadDTONotNull() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        assertNotNull(commandeReadDTO);
    }

    @Test
    public void testCommandeId() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setCommandeId(commandeId);
        assertEquals(commandeId, commandeReadDTO.getCommandeId());
    }

    @Test
    public void testNumero() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setNumero(numero);
        assertEquals(numero, commandeReadDTO.getNumero());
    }

    @Test
    public void testDateSaisie() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setDateSaisie(dateSaisie);
        assertEquals(dateSaisie, commandeReadDTO.getDateSaisie());
    }

    @Test
    public void testDateLivraison() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setDateLivraison(dateLivraison);
        assertEquals(dateLivraison, commandeReadDTO.getDateLivraison());
    }

    @Test
    public void testStatus() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setStatus(status);
        assertEquals(status, commandeReadDTO.getStatus());
    }

    @Test
    public void testSurPlace() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setSurPlace(surPlace);
        assertEquals(surPlace, commandeReadDTO.getSurPlace());
    }

    @Test
    public void testNbArticle() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setNbArticle(nbArticle);
        assertEquals(nbArticle, commandeReadDTO.getNbArticle());
    }

    @Test
    public void testPrixHT() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setPrixTTC(prixTTC);
        assertEquals(prixTTC, commandeReadDTO.getPrixTTC());
    }

    @Test
    public void testArticles() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setArticles(articles);
        assertEquals(articles, commandeReadDTO.getArticles());
    }

    @Test
    public void testMenus() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setMenus(menus);
        assertEquals(menus, commandeReadDTO.getMenus());
    }

    @Test
    public void testPaiementSet() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setPaiementSet(paiementSet);
        assertEquals(paiementSet, commandeReadDTO.getPaiementSet());
    }

    @Test
    public void testPaiementTypeCommande() {
        CommandeReadDTO commandeReadDTO = new CommandeReadDTO();
        commandeReadDTO.setPaiementTypeCommande(paiementTypeCommande);
        assertEquals(paiementTypeCommande, commandeReadDTO.getPaiementTypeCommande());
    }

    @Test
    public void testConvertedCommandeNotNull() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertNotNull(commandeReadDTO);
    }

    @Test
    public void testConvertedCommandeId() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getId(), commandeReadDTO.getCommandeId());
    }

    @Test
    public void testConvertedNumero() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getNumero(), commandeReadDTO.getNumero());
    }

    @Test
    public void testConvertedDateSaisie() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getDateSaisie(), commandeReadDTO.getDateSaisie());
    }

    @Test
    public void testConvertedDateLivraison() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getDateLivraison(), commandeReadDTO.getDateLivraison());
    }

    @Test
    public void testConvertedStatus() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getStatus(), commandeReadDTO.getStatus());
    }

    @Test
    public void testConvertedSurPlace() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getSurPlace(), commandeReadDTO.getSurPlace());
    }

    @Test
    public void testConvertedNbArticle() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getNbArticle(), commandeReadDTO.getNbArticle());
    }

    @Test
    public void testConvertedArticles() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getArticles(), commandeReadDTO.getArticles());
    }

    @Test
    public void testConvertedMenus() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getMenus(), commandeReadDTO.getMenus());
    }

    @Test
    public void testConvertedPaiementSet() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getPaiementSet(), commandeReadDTO.getPaiementSet());
    }

    @Test
    public void testConvertedPaiementTypeCommande() {
        CommandeReadDTO commandeReadDTO = dtoTools.convertToDto(commande, CommandeReadDTO.class);
        assertEquals(commande.getPaiementType(), commandeReadDTO.getPaiementTypeCommande());
    }
}
