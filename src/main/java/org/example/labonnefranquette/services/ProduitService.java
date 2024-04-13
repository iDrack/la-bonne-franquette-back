package org.example.labonnefranquette.services;


import org.example.labonnefranquette.model.Produit;

import java.util.List;

public interface ProduitService {

    List<Produit> getAllProduit();

    List<Produit> getProduitById(long id);

}
