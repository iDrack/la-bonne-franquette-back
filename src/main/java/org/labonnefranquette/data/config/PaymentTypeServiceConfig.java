package org.labonnefranquette.data.config;

import org.labonnefranquette.data.model.Category;
import org.labonnefranquette.data.model.PaymentType;
import org.labonnefranquette.data.repository.CategoryRepository;
import org.labonnefranquette.data.repository.PaymentTypeRepository;
import org.labonnefranquette.data.services.impl.GenericServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentTypeServiceConfig {

    @Bean
    public GenericServiceImpl<PaymentType, PaymentTypeRepository, Long> paymentTypeService(@Qualifier("paymentTypeRepository") PaymentTypeRepository paymentTypeRepository) {
        return new GenericServiceImpl<>(paymentTypeRepository);
    }
}
