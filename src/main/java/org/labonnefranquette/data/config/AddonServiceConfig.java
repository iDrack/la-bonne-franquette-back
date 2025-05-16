package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Addon;
import org.labonnefranquette.data.repository.AddonRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AddonServiceConfig {

    @Bean
    public GenericServiceImpl<Addon, AddonRepository, Long> addonService(@Qualifier("addonRepository") AddonRepository addonRepository) {
        return new GenericServiceImpl<>(addonRepository);
    }
}