-- Insertion des catégories

INSERT INTO categorie (id, nom, categorie_type)
VALUES (1, 'Burger', 'categorie'),
       (2, 'Wrap', 'categorie'),
       (3, 'Salade', 'categorie'),
       (4, 'Accompagnement', 'categorie'),
       (5, 'Boisson', 'categorie'),
       (6, 'Dessert', 'categorie');

-- Insertion des ingrédients avec le booléen a_cuire

INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
VALUES (1, 'Bœuf', TRUE, 'ingredient'),
       (2, 'Fromage cheddar', FALSE, 'ingredient'),
       (3, 'Laitue', FALSE, 'ingredient'),
       (4, 'Tomate', FALSE, 'ingredient'),
       (5, 'Cornichons', FALSE, 'ingredient'),
       (6, 'Oignons', FALSE, 'ingredient'),
       (7, 'Ketchup', FALSE, 'ingredient'),
       (8, 'Moutarde', FALSE, 'ingredient'),
       (9, 'Pain brioché', TRUE, 'ingredient'),
       (10, 'Bacon', TRUE, 'ingredient'),
       (11, 'Sauce spéciale', FALSE, 'ingredient'),
       (12, 'Poulet pané', TRUE, 'ingredient'),
       (13, 'Fromage suisse', FALSE, 'ingredient'),
       (14, 'Mayonnaise', FALSE, 'ingredient'),
       (15, 'Pain sésame', TRUE, 'ingredient'),
       (16, 'Galette de légumes', TRUE, 'ingredient'),
       (17, 'Avocat', FALSE, 'ingredient'),
       (18, 'Oignons rouges', FALSE, 'ingredient'),
       (19, 'Sauce yaourt', FALSE, 'ingredient'),
       (20, 'Pain complet', TRUE, 'ingredient'),
       (21, 'Poulet grillé', TRUE, 'ingredient'),
       (22, 'Parmesan', FALSE, 'ingredient'),
       (23, 'Sauce Caesar', FALSE, 'ingredient'),
       (24, 'Tortilla de blé', TRUE, 'ingredient'),
       (25, 'Bœuf épicé', TRUE, 'ingredient'),
       (26, 'Jalapenos', FALSE, 'ingredient'),
       (27, 'Sauce piquante', FALSE, 'ingredient'),
       (28, 'Concombre', FALSE, 'ingredient'),
       (29, 'Oignon rouge', FALSE, 'ingredient'),
       (30, 'Feta', FALSE, 'ingredient'),
       (31, 'Olives', FALSE, 'ingredient'),
       (32, 'Poivrons', FALSE, 'ingredient'),
       (33, 'Vinaigrette grecque', FALSE, 'ingredient'),
       (34, 'Maïs', FALSE, 'ingredient'),
       (35, 'Haricots noirs', FALSE, 'ingredient'),
       (36, 'Tomates cerises', FALSE, 'ingredient'),
       (37, 'Vinaigrette citronnée', FALSE, 'ingredient'),
       (38, 'Frites Classiques', TRUE, 'ingredient'),
       (39, 'Frites de Patate Douce', TRUE, 'ingredient'),
       (40, 'Onion Rings', TRUE, 'ingredient'),
       (41, 'Mozzarella Sticks', TRUE, 'ingredient'),
       (42, 'Sodas', FALSE, 'ingredient'),
       (43, 'Eau Minérale', FALSE, 'ingredient'),
       (44, 'Thé Glacé', FALSE, 'ingredient'),
       (45, 'Milkshake Vanille', FALSE, 'ingredient'),
       (46, 'Milkshake Chocolat', FALSE, 'ingredient'),
       (47, 'Milkshake Fraise', FALSE, 'ingredient'),
       (48, 'Cookie aux Pépites de Chocolat', TRUE, 'ingredient'),
       (49, 'Brownie', TRUE, 'ingredient'),
       (50, 'Tarte aux Pommes', TRUE, 'ingredient'),
       (51, 'Sundae Chocolat', FALSE, 'ingredient'),
       (52, 'Sundae Caramel', FALSE, 'ingredient'),
       (53, 'Sundae Fraise', FALSE, 'ingredient');

-- Insertion des produits

INSERT INTO produit (id, nom, prix_ht)
VALUES (1, 'Classic Cheeseburger', 550),
       (2, 'Double Bacon Burger', 750),
       (3, 'Chicken Deluxe', 600),
       (4, 'Veggie Burger', 600),
       (5, 'Chicken Caesar Wrap', 500),
       (6, 'Spicy Beef Wrap', 550),
       (7, 'Caesar Salad', 550),
       (8, 'Greek Salad', 500),
       (9, 'Veggie Delight Salad', 550),
       (10, 'Frites Classiques', 250),
       (11, 'Frites de Patate Douce', 300),
       (12, 'Onion Rings', 300),
       (13, 'Mozzarella Sticks', 400),
       (14, 'Sodas', 150),
       (15, 'Eau Minérale', 100),
       (16, 'Thé Glacé', 200),
       (17, 'Milkshake Vanille', 350),
       (18, 'Milkshake Chocolat', 350),
       (19, 'Milkshake Fraise', 350),
       (20, 'Cookie aux Pépites de Chocolat', 150),
       (21, 'Brownie', 200),
       (22, 'Tarte aux Pommes', 250),
       (23, 'Sundae Chocolat', 300),
       (24, 'Sundae Caramel', 300),
       (25, 'Sundae Fraise', 300);

-- Association des produits avec les catégories

INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
VALUES (1, 1),
       (2, 1),
       (3, 1),
       (4, 1),
       (5, 2),
       (6, 2),
       (7, 3),
       (8, 3),
       (9, 3),
       (10, 4),
       (11, 4),
       (12, 4),
       (13, 4),
       (14, 5),
       (15, 5),
       (16, 5),
       (17, 5),
       (18, 5),
       (19, 5),
       (20, 6),
       (21, 6),
       (22, 6),
       (23, 6),
       (24, 6),
       (25, 6);

-- Association des produits avec les ingrédients

INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (1, 4),
       (1, 5),
       (1, 6),
       (1, 7),
       (1, 8),
       (1, 9),
       (2, 1),
       (2, 1),
       (2, 2),
       (2, 10),
       (2, 3),
       (2, 4),
       (2, 6),
       (2, 11),
       (2, 9),
       (3, 12),
       (3, 13),
       (3, 3),
       (3, 4),
       (3, 14),
       (3, 15),
       (4, 16),
       (4, 17),
       (4, 3),
       (4, 4),
       (4, 18),
       (4, 19),
       (4, 20),
       (5, 21),
       (5, 3),
       (5, 22),
       (5, 23),
       (5, 24),
       (6, 1),
       (6, 25),
       (6, 2),
       (6, 3),
       (6, 4),
       (6, 26),
       (6, 27),
       (6, 24),
       (7, 3),
       (7, 21),
       (7, 22),
       (7, 23),
       (8, 3),
       (8, 28),
       (8, 29),
       (8, 30),
       (8, 31),
       (8, 32),
       (8, 33),
       (9, 16),
       (9, 17),
       (9, 3),
       (9, 28),
       (9, 34),
       (9, 35),
       (9, 36),
       (9, 37),
       (10, 38),
       (11, 39),
       (12, 40),
       (13, 41),
       (14, 42),
       (15, 43),
       (16, 44),
       (17, 45),
       (18, 46),
       (19, 47),
       (20, 48),
       (21, 49),
       (22, 50),
       (23, 51),
       (24, 52),
       (25, 53);

-- Insertion des menus

INSERT INTO menu (id, nom, prix_ht)
VALUES (1, 'Menu Burger Classique', 850),
       (2, 'Menu Double Bacon', 1050),
       (3, 'Menu Chicken Deluxe', 900),
       (4, 'Menu Wrap Caesar', 700),
       (5, 'Menu Spicy Beef', 750),
       (6, 'Menu Caesar Salad', 750),
       (7, 'Menu Greek Salad', 700),
       (8, 'Menu Veggie Delight', 750);

-- Association des menus avec les produits

INSERT INTO menu_contient_produit (menu_id, produit_id)
VALUES (1, 1),
       (1, 10),
       (1, 14),
       (2, 2),
       (2, 10),
       (2, 14),
       (3, 3),
       (3, 10),
       (3, 14),
       (4, 5),
       (4, 10),
       (4, 14),
       (5, 6),
       (5, 10),
       (5, 14),
       (6, 7),
       (6, 10),
       (6, 14),
       (7, 8),
       (7, 10),
       (7, 14),
       (8, 9),
       (8, 10),
       (8, 14);

-- Insertion des extras

INSERT INTO ingredient (ingredient_type, nom, prixht, a_cuire, ingredient_id)
VALUES ('extra', 'Bacon supplémentaire', 100, TRUE, 10),
       ('extra', 'Fromage supplémentaire', 50, FALSE, 2),
       ('extra', 'Avocat supplémentaire', 150, FALSE, 17),
       ('extra', 'Jalapenos supplémentaires', 075, FALSE, 26),
       ('extra', 'Oignons supplémentaires', 025, FALSE, 6);

-- Insertion des nouvelles catégories
INSERT INTO categorie (id, nom, categorie_type)
VALUES (7, 'Petit Déjeuner', 'categorie'),
       (8, 'Brunch', 'categorie');

-- Insertion des nouveaux ingrédients
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
VALUES (54, 'Œufs', TRUE, 'ingredient'),
       (55, 'Bacon', TRUE, 'ingredient'),
       (56, 'Pain complet', TRUE, 'ingredient'),
       (57, 'Beurre', FALSE, 'ingredient'),
       (58, 'Confiture', FALSE, 'ingredient');

-- Insertion des nouveaux produits
INSERT INTO produit (id, nom, prix_ht)
VALUES (26, 'Omelette', 400),
       (27, 'Pancakes', 350),
       (28, 'Toast', 200);

-- Association des nouveaux produits avec les nouvelles catégories
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
VALUES (26, 7),
       (27, 7),
       (28, 7);

-- Association des nouveaux produits avec les nouveaux ingrédients
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
VALUES (26, 54),
       (26, 55),
       (27, 57),
       (27, 58),
       (28, 56),
       (28, 57);

-- Insertion des nouveaux menus
INSERT INTO menu (id, nom, prix_ht)
VALUES (9, 'Menu Petit Déjeuner', 600),
       (10, 'Menu Brunch', 800);

-- Association des nouveaux menus avec les nouveaux produits
INSERT INTO menu_contient_produit (menu_id, produit_id)
VALUES (9, 26),
       (9, 27),
       (10, 26),
       (10, 28);

-- Insertion des nouveaux extras
INSERT INTO ingredient (ingredient_type, nom, prixht, a_cuire, ingredient_id)
VALUES ('extra', 'Sirop d\'érable', 50, FALSE, 58),
       ('extra', 'Fruits frais', 100, FALSE, 59);

-- Association des nouveaux extras avec les nouveaux produits
INSERT INTO produit_contient_extra (produit_id, extra_id)
VALUES (27, 58),
       (27, 59);