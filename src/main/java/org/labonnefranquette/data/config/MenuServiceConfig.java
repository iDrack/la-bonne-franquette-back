package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Menu;
import org.labonnefranquette.data.repository.MenuRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuServiceConfig {

    @Bean
    public GenericServiceImpl<Menu, MenuRepository, Long> menuService(@Qualifier("menuRepository") MenuRepository menuRepository) {
        return new GenericServiceImpl<>(menuRepository);
    }
}