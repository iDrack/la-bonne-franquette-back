-- user admin

INSERT INTO restaurant(id, name, version_carte)
VALUES (1, 'test', 1);

INSERT INTO users(username, password, roles, restaurant_id)
VALUES ('admin', '$2a$10$KV8cQULcx8Y2VNyf5lpOPuFdvJ4rzNmRFyDvgWDvhth.eVKAddo1y', 'ROLE_ADMIN', 1);

-- Insertion des catégories
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
VALUES (1, 'Burgers', 'categorie', 1),
       (2, 'Salades', 'categorie', 1),
       (3, 'Boissons', 'categorie', 1),
       (4, 'Desserts', 'categorie', 1),
       (5, 'Wraps', 'categorie', 1);

-- Insertion des sous-catégories
INSERT INTO categorie (id, nom, categorie_type, categorie_id, restaurant_id)
VALUES (6, 'Boeuf', 'sous-categorie', 1, 1),
       (7, 'Poulet', 'sous-categorie', 2, 1),
       (8, 'Eaux', 'sous-categorie', 3, 1),
       (9, 'Jus', 'sous-categorie', 3, 1),
       (10, 'Soda', 'sous-categorie', 3, 1);

-- Insertion des ingrédients
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
VALUES (1, 'Tomate', FALSE, 'ingredient', 1),
       (2, 'Fromage', FALSE, 'ingredient', 1),
       (3, 'Salade', FALSE, 'ingredient', 1),
       (4, 'Poulet', TRUE, 'ingredient', 1),
       (5, 'Boeuf', TRUE, 'ingredient', 1),
       (6, 'Eau', FALSE, 'ingredient', 1),
       (7, 'Cola', FALSE, 'ingredient', 1),
       (8, 'Citron', FALSE, 'ingredient', 1),
       (9, 'Pomme', FALSE, 'ingredient', 1),
       (10, 'Orange', FALSE, 'ingredient', 1),
       (11, 'Glaçon', FALSE, 'ingredient', 1);

-- Insertion des extras
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, prixHT, ingredient_id, restaurant_id)
VALUES (12, 'Bacon', TRUE, 'extra', 100, 1, 1),
       (13, 'Avocat', FALSE, 'extra', 150, 2, 1),
       (14, 'Glaçon', FALSE, 'extra', 150, 11, 1);

-- Insertion des produits
INSERT INTO produit (id, nom, prix_ht, restaurant_id)
VALUES (1, 'Burger Classique', 850, 1),
       (2, 'Double Bacon', 1050, 1),
       (3, 'Chicken Deluxe', 900, 1),
       (4, 'Wrap Caesar', 700, 1),
       (5, 'Spicy Beef', 750, 1),
       (6, 'Eau plate', 100, 1),
       (7, 'Eau gazeuse', 150, 1),
       (8, 'Jus de pomme', 200, 1),
       (9, 'Jus d\'orange', 200, 1),
       (10, 'Coca', 250, 1),
       (11, 'Limonade', 250, 1);

-- Association des produits avec les catégories
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
VALUES (1, 6),
       (2, 6),
       (3, 7),
       (4, 5),
       (5, 6),
       (6, 8),
       (7, 8),
       (8, 9),
       (9, 9),
       (10, 10),
       (11, 10);

-- Association des produits avec les ingrédients
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 5),
       (2, 1),
       (2, 2),
       (2, 5),
       (6, 6),
       (7, 6),
       (8, 9),
       (9, 10),
       (10, 7),
       (11, 8);

-- Association des produits avec les extras
INSERT INTO produit_contient_extra (produit_id, extra_id)
VALUES (1, 12),
       (2, 12),
       (3, 12),
       (3, 13),
       (4, 12),
       (5, 12),
       (6, 14),
       (7, 14),
       (8, 14),
       (9, 14),
       (10, 14),
       (11, 14);

-- Insertion des menus
INSERT INTO menu (id, nom, prix_ht, restaurant_id)
VALUES (1, 'Menu Burger Classique', 1000, 1),
       (2, 'Menu Double Bacon', 1200, 1);

-- Insertion des menu items
INSERT INTO menu_item (id, optional, extra_price, menu, restaurant_id)
VALUES (1, FALSE, 0, 1, 1),
       (2, TRUE, 200, 1, 1),
       (3, FALSE, 0, 2, 1),
       (4, FALSE, 0, 1, 1),
       (5, FALSE, 0, 2, 1);

-- Association des menu items avec les produits
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
VALUES (1, 1),
       (2, 3),
       (3, 2),
       (4, 6),
       (4, 7),
       (4, 8),
       (4, 9),
       (4, 10),
       (4, 11),
       (5, 6),
       (5, 7),
       (5, 8),
       (5, 9),
       (5, 10),
       (5, 11);

-- Insertion des type de paiement

INSERT INTO paiement_type_commande (id, name, is_enable, restaurant_id)
VALUES (1, 'CARTE BANCAIRE', true, 1),
       (2, 'CHEQUE', true, 1),
       (3, 'ESPECE', true, 1),
       (4, 'TICKET RESTAURANT', true, 1);
