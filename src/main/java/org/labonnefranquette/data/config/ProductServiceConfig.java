package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Product;
import org.labonnefranquette.data.repository.ProductRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProductServiceConfig {

    @Bean
    public GenericServiceImpl<Product, ProductRepository, Long> productService(@Qualifier("productRepository") ProductRepository productRepository) {
        return new GenericServiceImpl<>(productRepository);
    }
}