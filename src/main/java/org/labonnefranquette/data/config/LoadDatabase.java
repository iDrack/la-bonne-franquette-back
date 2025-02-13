package org.labonnefranquette.data.config;


import org.labonnefranquette.data.model.entity.PaiementTypeCommandeEntity;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.repository.PaiementTypeCommandeRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(PaiementTypeCommandeRepository repository) {
        return args -> {
            for (PaiementTypeCommande type : PaiementTypeCommande.values()) {
                if (!repository.existsByType(type)) {
                    PaiementTypeCommandeEntity entity = new PaiementTypeCommandeEntity();
                    entity.setType(type);
                    entity.setEnable(true);
                    repository.save(entity);
                }
            }
        };
    }
}