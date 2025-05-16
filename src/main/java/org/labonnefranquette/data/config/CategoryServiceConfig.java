package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.repository.CategoryRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CategoryServiceConfig {

    @Bean
    public GenericServiceImpl<Category, CategoryRepository, Long> categoryService(@Qualifier("categoryRepository") CategoryRepository categoryRepository) {
        return new GenericServiceImpl<>(categoryRepository);
    }
}