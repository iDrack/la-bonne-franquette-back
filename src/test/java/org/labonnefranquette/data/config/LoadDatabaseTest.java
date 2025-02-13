package org.labonnefranquette.data.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.labonnefranquette.data.model.entity.PaiementTypeCommandeEntity;
import org.labonnefranquette.data.model.enums.PaiementTypeCommande;
import org.labonnefranquette.data.repository.PaiementTypeCommandeRepository;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.CommandLineRunner;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoadDatabaseTest {

    @Mock
    private PaiementTypeCommandeRepository repository;

    @InjectMocks
    private LoadDatabase loadDatabase;

    private CommandLineRunner runner;

    @BeforeEach
    void setUp() {
        runner = loadDatabase.initDatabase(repository);
    }

    @Test
    void initDatabase_savesNewPaiementTypeCommandeEntities() throws Exception {
        for (PaiementTypeCommande type : PaiementTypeCommande.values()) {
            when(repository.existsByType(type)).thenReturn(false);
        }

        runner.run();

        for (PaiementTypeCommande type : PaiementTypeCommande.values()) {
            verify(repository).save(argThat(entity -> entity.getType() == type && entity.isEnable()));
        }
    }

    @Test
    void initDatabase_doesNotSaveExistingPaiementTypeCommandeEntities() throws Exception {
        for (PaiementTypeCommande type : PaiementTypeCommande.values()) {
            when(repository.existsByType(type)).thenReturn(true);
        }

        runner.run();

        verify(repository, never()).save(any(PaiementTypeCommandeEntity.class));
    }
}