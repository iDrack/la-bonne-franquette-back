package org.labonnefranquette.mongo.repository;

import org.labonnefranquette.mongo.model.Commande;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommandeRepository extends MongoRepository<Commande, Long> {
}
