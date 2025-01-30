-- user admin

INSERT INTO users(username, password, roles)
VALUES ('admin', '$2a$10$KV8cQULcx8Y2VNyf5lpOPuFdvJ4rzNmRFyDvgWDvhth.eVKAddo1y', 'ROLE_ADMIN');

-- Insertion des catégories
INSERT INTO categorie (id, nom, categorie_type)
VALUES (1, 'Burgers', 'categorie'),
       (2, 'Salades', 'categorie'),
       (3, 'Boissons', 'categorie'),
       (4, 'Desserts', 'categorie'),
       (5, 'Wraps', 'categorie');

-- Insertion des sous-catégories
INSERT INTO categorie (id, nom, categorie_type, categorie_id)
VALUES (6, 'Boeuf', 'sous-categorie', 1),
       (7, 'Poulet', 'sous-categorie', 2);

-- Insertion des ingrédients
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
VALUES (1, 'Tomate', FALSE, 'ingredient'),
       (2, 'Fromage', FALSE, 'ingredient'),
       (3, 'Salade', FALSE, 'ingredient'),
       (4, 'Poulet', TRUE, 'ingredient'),
       (5, 'Boeuf', TRUE, 'ingredient');

-- Insertion des extras
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, prixHT, ingredient_id)
VALUES (6, 'Bacon', TRUE, 'extra', 100, 1),
       (7, 'Avocat', FALSE, 'extra', 150, 2);

-- Insertion des produits
INSERT INTO produit (id, nom, prix_ht)
VALUES (1, 'Burger Classique', 850),
       (2, 'Double Bacon', 1050),
       (3, 'Chicken Deluxe', 900),
       (4, 'Wrap Caesar', 700),
       (5, 'Spicy Beef', 750);

-- Association des produits avec les catégories
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
VALUES (1, 6),
       (2, 6),
       (3, 7),
       (4, 5),
       (5, 6);

-- Association des produits avec les ingrédients
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 5),
       (2, 1),
       (2, 2),
       (2, 6);

-- Association des produits avec les extras
INSERT INTO produit_contient_extra (produit_id, extra_id)
VALUES (1, 6),
       (2, 6),
       (3, 6),
       (3, 7),
       (4, 6),
       (5, 6);

-- Insertion des menus
INSERT INTO menu (id, nom, prix_ht)
VALUES (1, 'Menu Burger Classique', 1000),
       (2, 'Menu Double Bacon', 1200);

-- Insertion des menu items
INSERT INTO menu_item (id, optional, extra_price, menu)
VALUES (1, FALSE, 0, 1),
       (2, TRUE, 200, 1),
       (3, FALSE, 0, 2);

-- Association des menu items avec les produits
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
VALUES (1, 1),
       (2, 3),
       (3, 2);
