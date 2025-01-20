package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Categorie;
import org.labonnefranquette.data.repository.CategorieRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategorieServiceConfig {

    @Bean
    public GenericServiceImpl<Categorie, CategorieRepository, Long> categorieService(@Qualifier("categorieRepository") CategorieRepository categorieRepository) {
        return new GenericServiceImpl<>(categorieRepository);
    }
}