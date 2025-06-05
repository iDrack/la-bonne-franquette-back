package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.MenuItem;
import org.labonnefranquette.data.repository.MenuItemRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MenuItemServiceConfig {
    @Bean
    public GenericServiceImpl<MenuItem, MenuItemRepository, Long> menuItemService(@Qualifier("menuItemRepository") MenuItemRepository menuItemRepository) {
        return new GenericServiceImpl<>(menuItemRepository);
    }
}
