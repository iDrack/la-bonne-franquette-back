package org.labonnefranquette.data.services;


import org.labonnefranquette.data.model.Produit;

import java.util.List;
import java.util.Optional;

public interface ProduitService {

    List<Produit> getAllProduit();

    Optional<Produit> getProduitById(long id);

    Produit createProduit(Produit newProduit);

}
