package org.example.labonnefranquette.services;


import org.example.labonnefranquette.model.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitService {

    List<Produit> getAllProduit();

    Optional<Produit> getProduitById(long id);

    Produit createProduit(Produit newProduit);

}
