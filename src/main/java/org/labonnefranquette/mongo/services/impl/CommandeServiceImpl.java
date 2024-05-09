package org.labonnefranquette.mongo.services.impl;

import com.mongodb.client.result.UpdateResult;
import org.labonnefranquette.mongo.model.Commande;
import org.labonnefranquette.mongo.model.StatusCommande;
import org.labonnefranquette.mongo.repository.CommandeRepository;
import org.labonnefranquette.mongo.services.CommandeService;
import org.labonnefranquette.sql.model.Paiement;
import org.labonnefranquette.sql.repository.ExtraRepository;
import org.labonnefranquette.sql.repository.MenuRepository;
import org.labonnefranquette.sql.repository.PaiementRepository;
import org.labonnefranquette.sql.repository.ProduitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CommandeServiceImpl implements CommandeService {

    @Autowired
    CommandeRepository commandeRepository;
    @Autowired
    ProduitRepository produitRepository;
    @Autowired
    ExtraRepository extraRepository;
    @Autowired
    MenuRepository menuRepository;
    @Autowired
    PaiementRepository paiementRepository;
    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Commande> findAllCommande() {
        return commandeRepository.findAll();
    }

    @Override
    public Optional<Commande> findCommandeById(Long id) {
        return commandeRepository.findById(id);
    }

    /**
     * Permet de créer une commande à partir des éléments passés en paramètre.
     *
     * @param commande, Commande à enregistrer,
     * @return Commande, La commande créée grâce aux infos passées en paramètre
     */
    @Override
    public Commande createCommande(Commande commande) {
        commandeRepository.save(commande);
        return commande;
    }

    @Override
    public Boolean deleteCommande(Long id) {
        Optional<Commande> commandeFound = findCommandeById(id);
        if (commandeFound.isEmpty()) {
            return false;
        }
        commandeRepository.delete(commandeFound.get());
        return true;
    }

    @Override
    public Commande updateCommande(Commande commande) {
        Optional<Commande> commandeFound = findCommandeById(commande.getCommandeId());
        if (commandeFound.isEmpty()) {
            return null;
        }
        Commande currentCommande = commandeFound.get();
        currentCommande = updateCommandeByField(currentCommande, "numero", commande.getNumero());
        currentCommande = updateCommandeByField(currentCommande, "surPlace", commande.getSurPlace());
        currentCommande = updateCommandeByField(currentCommande, "menuSet", commande.getMenuSet());
        currentCommande = updateCommandeByField(currentCommande, "status", commande.getStatus());
        currentCommande = updateProduitsEtExtras(currentCommande, commande.getProduitsAvecExtras());

        return currentCommande;
    }

    @Override
    public Commande ajoutPaiement(Commande commande, Long paiementId) {
        Optional<Commande> commandeFound = findCommandeById(commande.getCommandeId());
        Optional<Paiement> paiementFound = paiementRepository.findById(paiementId);
        if (commandeFound.isEmpty() || paiementFound.isEmpty()) {
            return null;
        }
        UpdateResult result = mongoTemplate.updateFirst(
                Query.query(Criteria.where("commandeId").is(commande.getCommandeId())),
                Update.update("paiementSet", commande.getPaiementSet().add(paiementId)),
                Commande.class);
        if (result.wasAcknowledged()) {
            return findCommandeById(commande.getCommandeId()).get();
        }
        return null;
    }

    @Override
    public Commande updateProduitsEtExtras(Commande commande, List<List<Long>> produitsEtExtrasIds) {
        commande = updateCommandeByField(commande, "produitsAvecExtras", produitsEtExtrasIds);
        return updateCommandeByField(commande, "nbArticle", produitsEtExtrasIds.size());
    }

    /**
     * Permet d'éxécuter une requête de mise à jour sur une commande passée en paramètre sur un champ spécifique.
     * <T> est un générique correspondant au type de la valeur à modifier.
     *
     * @param commande:  Commande à modifier
     * @param fieldName: Nom du champ à modifier
     * @param value:     Nouvelle valeur
     * @param <T>:       Type de la valeur
     * @return
     */
    @Override
    public <T> Commande updateCommandeByField(Commande commande, String fieldName, T value) {
        Optional<Commande> commandeFound = findCommandeById(commande.getCommandeId());
        if (commandeFound.isEmpty()) {
            return null;
        }
        UpdateResult result = mongoTemplate.updateFirst(
                Query.query(Criteria.where("commandeId").is(commande.getCommandeId())),
                Update.update(fieldName, value),
                Commande.class);
        if (result.wasAcknowledged()) {
            return findCommandeById(commande.getCommandeId()).get();
        }
        return null;
    }

    @Override
    public Commande advanceStatusCommande(Commande commande) throws RuntimeException {
        if (commande.getStatus() != StatusCommande.EN_COURS)
            throw new RuntimeException("Commande n'est pas en cours.");
        return updateCommandeByField(commande, "status", StatusCommande.TERMINE);
    }
}
