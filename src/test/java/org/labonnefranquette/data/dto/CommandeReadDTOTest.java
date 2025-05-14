package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.OrderReadDTO;
import org.labonnefranquette.data.model.Order;
import org.labonnefranquette.data.model.Payment;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.OrderStatus;
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
    private OrderStatus status;
    private Boolean surPlace;
    private int nbArticle;
    private int prixTTC;
    private Collection<Article> articles;
    private Collection<Selection> menus;
    private Collection<Payment> paiementSet;
    private String paiementTypeCommande;
    private DtoTools dtoTools;
    private Order commande;

    @BeforeEach
    public void setUp() {
        commandeId = 1L;
        numero = 123;
        dateSaisie = new Date();
        dateLivraison = new Date();
        status = OrderStatus.EN_COURS;
        surPlace = true;
        nbArticle = 5;
        prixTTC = 100;
        articles = new ArrayList<>();
        menus = new ArrayList<>();
        paiementSet = new ArrayList<>();
        paiementTypeCommande = "CB";
        dtoTools = new DtoTools();
        commande = new Order();
        commande.setId(commandeId);
        commande.setNumero(numero);
        commande.setDateSaisie(dateSaisie);
        commande.setDateLivraison(dateLivraison);
        commande.setStatus(status);
        commande.setSurPlace(surPlace);
        commande.setNbArticle(nbArticle);
        commande.setTotalPrice(prixTTC);
        commande.setArticles(articles);
        commande.setMenus(menus);
        commande.setPaiementSet(paiementSet);
        commande.setPaiementType("CB");
    }

    @Test
    public void testCommandeReadDTONotNull() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        assertNotNull(commandeReadDTO);
    }

    @Test
    public void testCommandeId() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setCommandeId(commandeId);
        assertEquals(commandeId, commandeReadDTO.getCommandeId());
    }

    @Test
    public void testNumero() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setNumero(numero);
        assertEquals(numero, commandeReadDTO.getNumero());
    }

    @Test
    public void testDateSaisie() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setDateSaisie(dateSaisie);
        assertEquals(dateSaisie, commandeReadDTO.getDateSaisie());
    }

    @Test
    public void testDateLivraison() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setDateLivraison(dateLivraison);
        assertEquals(dateLivraison, commandeReadDTO.getDateLivraison());
    }

    @Test
    public void testStatus() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setStatus(status);
        assertEquals(status, commandeReadDTO.getStatus());
    }

    @Test
    public void testSurPlace() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setSurPlace(surPlace);
        assertEquals(surPlace, commandeReadDTO.getSurPlace());
    }

    @Test
    public void testNbArticle() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setNbArticle(nbArticle);
        assertEquals(nbArticle, commandeReadDTO.getNbArticle());
    }

    @Test
    public void testPrixHT() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setPrixTTC(prixTTC);
        assertEquals(prixTTC, commandeReadDTO.getPrixTTC());
    }

    @Test
    public void testArticles() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setArticles(articles);
        assertEquals(articles, commandeReadDTO.getArticles());
    }

    @Test
    public void testMenus() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setMenus(menus);
        assertEquals(menus, commandeReadDTO.getMenus());
    }

    @Test
    public void testPaiementSet() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setPaiementSet(paiementSet);
        assertEquals(paiementSet, commandeReadDTO.getPaiementSet());
    }

    @Test
    public void testPaiementTypeCommande() {
        OrderReadDTO commandeReadDTO = new OrderReadDTO();
        commandeReadDTO.setPaiementTypeCommande(paiementTypeCommande);
        assertEquals(paiementTypeCommande, commandeReadDTO.getPaiementTypeCommande());
    }

    @Test
    public void testConvertedCommandeNotNull() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertNotNull(commandeReadDTO);
    }

    @Test
    public void testConvertedCommandeId() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getId(), commandeReadDTO.getCommandeId());
    }

    @Test
    public void testConvertedNumero() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getNumero(), commandeReadDTO.getNumero());
    }

    @Test
    public void testConvertedDateSaisie() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getDateSaisie(), commandeReadDTO.getDateSaisie());
    }

    @Test
    public void testConvertedDateLivraison() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getDateLivraison(), commandeReadDTO.getDateLivraison());
    }

    @Test
    public void testConvertedStatus() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getStatus(), commandeReadDTO.getStatus());
    }

    @Test
    public void testConvertedSurPlace() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getSurPlace(), commandeReadDTO.getSurPlace());
    }

    @Test
    public void testConvertedNbArticle() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getNbArticle(), commandeReadDTO.getNbArticle());
    }

    @Test
    public void testConvertedArticles() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getArticles(), commandeReadDTO.getArticles());
    }

    @Test
    public void testConvertedMenus() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getMenus(), commandeReadDTO.getMenus());
    }

    @Test
    public void testConvertedPaiementSet() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getPaiementSet(), commandeReadDTO.getPaiementSet());
    }

    @Test
    public void testConvertedPaiementTypeCommande() {
        OrderReadDTO commandeReadDTO = dtoTools.convertToDto(commande, OrderReadDTO.class);
        assertEquals(commande.getPaiementType(), commandeReadDTO.getPaiementTypeCommande());
    }
}
