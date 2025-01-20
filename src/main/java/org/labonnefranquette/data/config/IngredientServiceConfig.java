package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Ingredient;
import org.labonnefranquette.data.repository.IngredientRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IngredientServiceConfig {

    @Bean
    public GenericServiceImpl<Ingredient, IngredientRepository, Long> ingredientService(@Qualifier("ingredientRepository") IngredientRepository ingredientRepository) {
        return new GenericServiceImpl<>(ingredientRepository);
    }
}