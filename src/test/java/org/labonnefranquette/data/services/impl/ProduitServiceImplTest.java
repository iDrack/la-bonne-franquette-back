package org.labonnefranquette.data.services.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.repository.ProduitRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class ProduitServiceImplTest {

    @Mock
    private ProduitRepository produitRepository;

    @InjectMocks
    private ProduitServiceImpl produitService;

    private Produit produit;

    @BeforeEach
    public void setup() {
        produit = new Produit();
        produit.setId(1L);
        produit.setNom("Test");
    }

    @Test
    public void findAllProduitsSuccessfully() {
        when(produitRepository.findAll()).thenReturn(Arrays.asList(produit));

        List<Produit> result = produitService.findAll();

        assertEquals(1, result.size());
        assertEquals(produit, result.get(0));
    }

    @Test
    public void findProduitByIdSuccessfully() {
        when(produitRepository.findById(anyLong())).thenReturn(Optional.of(produit));

        Optional<Produit> result = produitService.findAllById(1L);

        assertTrue(result.isPresent());
        assertEquals(produit, result.get());
    }

    @Test
    public void findProduitByIdNotFound() {
        when(produitRepository.findById(anyLong())).thenReturn(Optional.empty());

        Optional<Produit> result = produitService.findAllById(1L);

        assertFalse(result.isPresent());
    }

    @Test
    public void createProduitSuccessfully() {
        when(produitRepository.save(any(Produit.class))).thenReturn(produit);

        Produit result = produitService.create(produit);

        assertEquals(produit, result);
    }

    @Test
    public void deleteProduitByIdSuccessfully() {
        doNothing().when(produitRepository).deleteById(anyLong());

        produitService.deleteById(1L);

        verify(produitRepository, times(1)).deleteById(anyLong());
    }
}