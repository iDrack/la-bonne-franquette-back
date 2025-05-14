package org.labonnefranquette.data.dto;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.labonnefranquette.data.dto.impl.OrderCreateDTO;
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
public class CommandeCreateDTOTest {

    private Boolean surPlace;
    private Collection<Selection> menus;
    private Collection<Payment> paiementSet;
    private OrderStatus status;
    private Collection<Article> articles;
    private int prixHT;
    private DtoTools dtoTools;
    private OrderCreateDTO commandeCreateDTO;

    @BeforeEach
    public void setUp() {
        surPlace = true;
        menus = new ArrayList<>();
        paiementSet = new ArrayList<>();
        status = OrderStatus.EN_COURS;
        articles = new ArrayList<>();
        prixHT = 100;
        dtoTools = new DtoTools();
        commandeCreateDTO = new OrderCreateDTO(surPlace, new Date(), new Date(), status, prixHT, articles, menus);
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
        Order commande = dtoTools.convertToEntity(commandeCreateDTO, Order.class);
        assertNotNull(commande);
    }

    @Test
    public void testConvertedSurPlace() {
        Order commande = dtoTools.convertToEntity(commandeCreateDTO, Order.class);
        assertEquals(surPlace, commande.getSurPlace());
    }

    @Test
    public void testConvertedMenus() {
        Order commande = dtoTools.convertToEntity(commandeCreateDTO, Order.class);
        assertEquals(menus, commande.getMenus());
    }

    @Test
    public void testConvertedPaiementSet() {
        Order commande = dtoTools.convertToEntity(commandeCreateDTO, Order.class);
        assertEquals(paiementSet, commande.getPaiementSet());
    }

    @Test
    public void testConvertedStatus() {
        Order commande = dtoTools.convertToEntity(commandeCreateDTO, Order.class);
        assertEquals(status, commande.getStatus());
    }

    @Test
    public void testConvertedArticles() {
        Order commande = dtoTools.convertToEntity(commandeCreateDTO, Order.class);
        assertEquals(articles, commande.getArticles());
    }

    @Test
    public void testConvertedPrixTTC() {
        Order commande = dtoTools.convertToEntity(commandeCreateDTO, Order.class);
        assertEquals(prixHT, commande.getTotalPrice());
    }
}
