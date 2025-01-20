package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Produit;
import org.labonnefranquette.data.repository.ProduitRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProduitServiceConfig {

    @Bean
    public GenericServiceImpl<Produit, ProduitRepository, Long> produitService(@Qualifier("produitRepository") ProduitRepository produitRepository) {
        return new GenericServiceImpl<>(produitRepository);
    }
}