CREATE
DATABASE IF NOT EXISTS `franquette-bdd-test`;
USE
`franquette-bdd-test`;

-- Création de la table restaurant
CREATE TABLE IF NOT EXISTS restaurant
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    name
    VARCHAR
(
    100
) NOT NULL,
    version_carte INT NOT NULL DEFAULT 1,
    tva_enable BOOLEAN NOT NULL
    );

-- Création de la table produit
CREATE TABLE IF NOT EXISTS produit
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    nom
    VARCHAR
(
    50
) NOT NULL,
    prix_ht DECIMAL
(
    10,
    2
) NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table menu
CREATE TABLE IF NOT EXISTS menu
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    nom
    VARCHAR
(
    50
) NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table ingredient
CREATE TABLE IF NOT EXISTS ingredient
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    nom
    VARCHAR
(
    50
) NOT NULL,
    a_cuire BOOLEAN NOT NULL,
    ingredient_type VARCHAR
(
    255
) NOT NULL,
    prixHT DECIMAL
(
    10,
    2
),
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table categorie
CREATE TABLE IF NOT EXISTS categorie
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    nom
    VARCHAR
(
    50
) NOT NULL,
    categorie_type VARCHAR
(
    255
) NOT NULL,
    categorie_id INT,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table sous_categorie
CREATE TABLE IF NOT EXISTS sous_categorie
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    nom
    VARCHAR
(
    50
) NOT NULL,
    categorie_id INT,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    categorie_id
) REFERENCES categorie
(
    id
),
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table extra
CREATE TABLE IF NOT EXISTS extra
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    nom
    VARCHAR
(
    50
) NOT NULL,
    prix_ht DECIMAL
(
    10,
    2
) NOT NULL,
    prix_ttc DECIMAL
(
    10,
    2
) DEFAULT 0,
    taux_tva VARCHAR
(
    255
) NOT NULL,
    ingredient_id INT,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    ingredient_id
) REFERENCES ingredient
(
    id
),
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table paiement_type
CREATE TABLE IF NOT EXISTS paiement_type
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    name
    VARCHAR
(
    50
) NOT NULL,
    is_enable BOOLEAN NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table paiement
CREATE TABLE IF NOT EXISTS paiement
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    date
    DATE
    NOT
    NULL,
    type_id
    INT
    NOT
    NULL,
    ticket_envoye
    BOOLEAN
    NOT
    NULL,
    prix_ht
    DECIMAL
(
    10,
    2
) NOT NULL,
    prix_ttc DECIMAL
(
    10,
    2
) NOT NULL,
    commande_id INT,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    type_id
) REFERENCES paiement_type
(
    id
),
    FOREIGN KEY
(
    commande_id
) REFERENCES commande
(
    id
),
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table commande
CREATE TABLE IF NOT EXISTS commande
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    numero
    SMALLINT
    NOT
    NULL,
    date_saisie
    DATE
    NOT
    NULL,
    date_livraison
    DATE,
    status
    VARCHAR
(
    50
) NOT NULL,
    sur_place BOOLEAN NOT NULL,
    nb_article INT NOT NULL,
    prix_ht DECIMAL
(
    10,
    2
) NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Création de la table user
CREATE TABLE IF NOT EXISTS users
(
    id
    INT
    PRIMARY
    KEY
    AUTO_INCREMENT,
    username
    VARCHAR
(
    100
) NOT NULL,
    password VARCHAR
(
    255
) NOT NULL,
    last_connection DATE,
    roles VARCHAR
(
    100
) NOT NULL,
    restaurant_id INT NOT NULL,
    FOREIGN KEY
(
    restaurant_id
) REFERENCES restaurant
(
    id
)
    );

-- Insertion des données
-- Restaurants
INSERT INTO restaurant (id, name, version_carte, tva_enable)
VALUES (1, 'Restaurant1', 1, true);

-- Produits
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (1, 'Produit1', 550, 1);
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (2, 'Produit2', 750, 1);
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (3, 'Produit3', 600, 1);
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (4, 'Produit4', 600, 1);
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (5, 'Produit5', 500, 1);
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (6, 'Produit6', 550, 1);
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (7, 'Produit7', 550, 1);
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (8, 'Produit8', 500, 1);

-- Menus
INSERT INTO menu (id, nom, restaurant_id)
VALUES (1, 'Menu1', 1);
INSERT INTO menu (id, nom, restaurant_id)
VALUES (2, 'Menu2', 1);
INSERT INTO menu (id, nom, restaurant_id)
VALUES (3, 'Menu3', 1);
INSERT INTO menu (id, nom, restaurant_id)
VALUES (4, 'Menu4', 1);
INSERT INTO menu (id, nom, restaurant_id)
VALUES (5, 'Menu5', 1);

-- Ingrédients
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, prixHT, restaurant_id)
VALUES (1, 'Ingredient1', false, 'ingredient', NULL, 1);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, prixHT, restaurant_id)
VALUES (2, 'Ingredient2', true, 'ingredient', NULL, 1);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, prixHT, restaurant_id)
VALUES (3, 'Ingredient3', false, 'ingredient', NULL, 1);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, prixHT, restaurant_id)
VALUES (4, 'Ingredient4', true, 'ingredient', NULL, 1);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, prixHT, restaurant_id)
VALUES (5, 'Ingredient5', false, 'ingredient', NULL, 1);

-- Extras
INSERT INTO extra (id, nom, prix_ht, prix_ttc, taux_tva, ingredient_id, restaurant_id)
VALUES (6, 'Extra1', 100, 0, 'AUCUN', 1, 1);
INSERT INTO extra (id, nom, prix_ht, prix_ttc, taux_tva, ingredient_id, restaurant_id)
VALUES (7, 'Extra2', 200, 0, 'AUCUN', 2, 1);
INSERT INTO extra (id, nom, prix_ht, prix_ttc, taux_tva, ingredient_id, restaurant_id)
VALUES (8, 'Extra3', 150, 0, 'AUCUN', 3, 1);
INSERT INTO extra (id, nom, prix_ht, prix_ttc, taux_tva, ingredient_id, restaurant_id)
VALUES (9, 'Extra4', 250, 0, 'AUCUN', 4, 1);
INSERT INTO extra (id, nom, prix_ht, prix_ttc, taux_tva, ingredient_id, restaurant_id)
VALUES (10, 'Extra5', 300, 0, 'AUCUN', 5, 1);

-- Catégories
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
VALUES (1, 'Categorie1', 'categorie', 1);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
VALUES (2, 'Categorie2', 'categorie', 1);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
VALUES (3, 'Categorie3', 'categorie', 1);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
VALUES (4, 'Categorie4', 'categorie', 1);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
VALUES (5, 'Categorie5', 'categorie', 1);

-- Sous-Catégories
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (1, 'SousCategorie1', 1, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (2, 'SousCategorie2', 1, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (3, 'SousCategorie3', 1, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (4, 'SousCategorie4', 2, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (5, 'SousCategorie5', 3, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (6, 'SousCategorie6', 3, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (7, 'SousCategorie7', 3, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (8, 'SousCategorie8', 4, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (9, 'SousCategorie9', 5, 1);
INSERT INTO sous_categorie (id, nom, categorie_id, restaurant_id)
VALUES (10, 'SousCategorie10', 5, 1);

-- Types de paiement
INSERT INTO paiement_type (id, name, is_enable, restaurant_id)
VALUES (1, 'Type1', true, 1);
INSERT INTO paiement_type (id, name, is_enable, restaurant_id)
VALUES (2, 'Type2', true, 1);
INSERT INTO paiement_type (id, name, is_enable, restaurant_id)
VALUES (3, 'Type3', true, 1);

-- Utilisateurs
INSERT INTO users (id, username, password, last_connection, roles, restaurant_id)
VALUES (1, 'user1', 'password1', NULL, 'ROLE_USER', 1);
INSERT INTO users (id, username, password, last_connection, roles, restaurant_id)
VALUES (2, 'user2', 'password2', NULL, 'ROLE_USER', 1);
INSERT INTO users (id, username, password, last_connection, roles, restaurant_id)
VALUES (3, 'user3', 'password3', NULL, 'ROLE_USER', 1);