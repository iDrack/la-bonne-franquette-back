package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.exception.PriceException;
import org.labonnefranquette.data.model.Commande;
import org.labonnefranquette.data.model.Paiement;
import org.labonnefranquette.data.model.entity.Article;
import org.labonnefranquette.data.model.entity.Selection;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.model.enums.StatusCommande;
import org.labonnefranquette.data.repository.CommandeRepository;
import org.labonnefranquette.data.utils.CommandeTools;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CommandeServiceImplTest {

    @Mock
    private CommandeRepository commandeRepository;

    @Mock
    private CommandeTools commandeTools;

    @InjectMocks
    private CommandeServiceImpl commandeService;

    private Commande commande;

    @BeforeEach
    public void setup() {
        commande = new Commande();
        commande.setId(1L);
        commande.setDateSaisie(new Date());
        commande.setNbArticle(1);
        commande.setNumero((short) 123);
        commande.setPaiementType(PaiementTypeCommande.ESP);
        commande.setStatus(StatusCommande.EN_COURS);
        commande.setPaiementSet(new ArrayList<Paiement>());
        commande.setArticles(List.of(new Article()));
        commande.setMenus(List.of(new Selection()));
    }

    @Test
    public void findAllCommandeSuccessfully() {
        when(commandeRepository.findAll()).thenReturn(Arrays.asList(commande));

        List<Commande> result = commandeService.findAllCommande();

        assertEquals(1, result.size());
        assertEquals(commande, result.get(0));
    }

    @Test
    public void findCommandeByIdSuccessfully() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));

        Optional<Commande> result = commandeService.findCommandeById(1L);

        assertTrue(result.isPresent());
        assertEquals(commande, result.get());
    }

    @Test
    public void createCommandeSuccessfully() throws PriceException {
        when(commandeTools.isCorrectPrice(any(Commande.class))).thenReturn(true);
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);

        Commande result = commandeService.createCommande(commande);

        assertEquals(commande, result);
    }

    @Test
    public void deleteCommandeSuccessfully() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        doNothing().when(commandeRepository).deleteById(anyLong());

        Boolean result = commandeService.deleteCommande(1L);

        assertTrue(result);
    }

    @Test
    public void ajoutPaiementSuccessfully() {
        Paiement paiement = new Paiement();
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);

        Commande result = commandeService.ajoutPaiement(commande, paiement);

        assertEquals(commande, result);
    }

    @Test
    public void advanceStatusCommandeSuccessfully() {
        when(commandeRepository.findById(anyLong())).thenReturn(Optional.of(commande));
        when(commandeRepository.save(any(Commande.class))).thenReturn(commande);

        Commande result = commandeService.advanceStatusCommande(commande.getId());

        assertEquals(commande, result);
    }
}