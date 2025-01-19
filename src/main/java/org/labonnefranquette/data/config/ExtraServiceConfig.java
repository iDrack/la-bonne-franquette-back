package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Extra;
import org.labonnefranquette.data.repository.ExtraRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExtraServiceConfig {

    @Bean
    public GenericServiceImpl<Extra, ExtraRepository, Long> extraService(@Qualifier("extraRepository") ExtraRepository extraRepository) {
        return new GenericServiceImpl<>(extraRepository);
    }
}