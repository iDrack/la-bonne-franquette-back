-- user admin

INSERT INTO restaurant(id, name, version_carte, tva_enable)
VALUES (1, 'test', 1, true);

INSERT INTO users(username, password, roles, restaurant_id)
VALUES ('admin', '$2a$10$KV8cQULcx8Y2VNyf5lpOPuFdvJ4rzNmRFyDvgWDvhth.eVKAddo1y', 'ROLE_ADMIN', 1);

-- Insertion des catégories
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
SELECT 1, 'Burgers', 'categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 1);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
SELECT 2, 'Salades', 'categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 2);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
SELECT 3, 'Boissons', 'categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 3);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
SELECT 4, 'Desserts', 'categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 4);
INSERT INTO categorie (id, nom, categorie_type, restaurant_id)
SELECT 5, 'Wraps', 'categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);

-- Insertion des sous-catégories
INSERT INTO categorie (id, nom, categorie_type, categorie_id, restaurant_id)
SELECT 6, 'Boeuf', 'sous-categorie', 1, 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 6);
INSERT INTO categorie (id, nom, categorie_type, categorie_id, restaurant_id)
SELECT 7, 'Poulet', 'sous-categorie', 2, 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 7);
INSERT INTO categorie (id, nom, categorie_type, categorie_id, restaurant_id)
SELECT 8, 'Eaux', 'sous-categorie', 3, 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 8);
INSERT INTO categorie (id, nom, categorie_type, categorie_id, restaurant_id)
SELECT 9, 'Jus', 'sous-categorie', 3, 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 9);
INSERT INTO categorie (id, nom, categorie_type, categorie_id, restaurant_id)
SELECT 10, 'Soda', 'sous-categorie', 3, 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 10);

-- Insertion des ingrédients
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 1, 'Tomate', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 1);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 2, 'Fromage', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 2);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 3, 'Salade', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 3);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 4, 'Poulet', TRUE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 4);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 5, 'Boeuf', TRUE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 5);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 6, 'Eau', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 6);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 7, 'Cola', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 7);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 8, 'Citron', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 8);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 9, 'Pomme', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 9);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 10, 'Orange', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 10);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type, restaurant_id)
SELECT 11, 'Glaçon', FALSE, 'ingredient', 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 11);

-- Insertion des extras
INSERT INTO extra (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 12, 'Bacon', 100, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 12);
INSERT INTO extra (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 13, 'Avocat', 150, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 13);
INSERT INTO extra (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 14, 'Glaçon', 150, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 14);

-- Insertion des produits
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 1, 'Burger Classique', 850, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 1);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 2, 'Double Bacon', 1050, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 2);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 3, 'Chicken Deluxe', 900, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 3);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 4, 'Wrap Caesar', 700, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 4);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 5, 'Spicy Beef', 750, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 5);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 6, 'Eau plate', 100, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 6);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 7, 'Eau gazeuse', 150, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 7);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 8, 'Jus de pomme', 200, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 8);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 9, 'Jus d\'orange', 200, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 9);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 10, 'Coca', 250, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 10);
INSERT INTO produit (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 11, 'Limonade', 250, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 11);

-- Association des produits avec les catégories
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 1, 6
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 1 AND categorie_id = 6);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 2, 6
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 2 AND categorie_id = 6);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 3, 7
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 3 AND categorie_id = 7);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 4, 5
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 4 AND categorie_id = 5);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 5, 6
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 5 AND categorie_id = 6);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 6, 8
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 6 AND categorie_id = 8);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 7, 8
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 7 AND categorie_id = 8);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 8, 9
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 8 AND categorie_id = 9);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 9, 9
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 9 AND categorie_id = 9);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 10, 10
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 10 AND categorie_id = 10);
INSERT INTO produit_appartient_categorie (produit_id, categorie_id)
SELECT 11, 10
WHERE NOT EXISTS (SELECT 1 FROM produit_appartient_categorie WHERE produit_id = 11 AND categorie_id = 10);

-- Association des produits avec les ingrédients
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 1 AND ingredient_id = 1);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 1, 2
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 1 AND ingredient_id = 2);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 1, 3
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 1 AND ingredient_id = 3);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 1, 5
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 1 AND ingredient_id = 5);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 2, 1
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 2 AND ingredient_id = 1);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 2, 2
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 2 AND ingredient_id = 2);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 2, 5
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 2 AND ingredient_id = 5);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 6, 6
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 6 AND ingredient_id = 6);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 7, 6
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 7 AND ingredient_id = 6);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 8, 9
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 8 AND ingredient_id = 9);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 9, 10
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 9 AND ingredient_id = 10);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 10, 7
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 10 AND ingredient_id = 7);
INSERT INTO produit_contient_ingredient (produit_id, ingredient_id)
SELECT 11, 8
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_ingredient WHERE produit_id = 11 AND ingredient_id = 8);

-- Association des produits avec les extras
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 1, 12
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 1 AND extra_id = 12);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 2, 12
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 2 AND extra_id = 12);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 3, 12
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 3 AND extra_id = 12);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 3, 13
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 3 AND extra_id = 13);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 4, 12
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 4 AND extra_id = 12);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 5, 12
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 5 AND extra_id = 12);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 6, 14
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 6 AND extra_id = 14);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 7, 14
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 7 AND extra_id = 14);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 8, 14
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 8 AND extra_id = 14);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 9, 14
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 9 AND extra_id = 14);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 10, 14
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 10 AND extra_id = 14);
INSERT INTO produit_contient_extra (produit_id, extra_id)
SELECT 11, 14
WHERE NOT EXISTS (SELECT 1 FROM produit_contient_extra WHERE produit_id = 11 AND extra_id = 14);

-- Insertion des menus
INSERT INTO menu (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 1, 'Menu Burger Classique', 1000, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE id = 1);
INSERT INTO menu (id, nom, prix_ht, taux_tva, restaurant_id)
SELECT 2, 'Menu Double Bacon', 1200, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE id = 2);

-- Insertion des menu items
INSERT INTO menu_item (id, optional, prix_ht, menu, taux_tva, restaurant_id)
SELECT 1, FALSE, 0, 1, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = 1);
INSERT INTO menu_item (id, optional, prix_ht, menu, taux_tva, restaurant_id)
SELECT 2, TRUE, 200, 1, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = 2);
INSERT INTO menu_item (id, optional, prix_ht, menu, taux_tva, restaurant_id)
SELECT 3, FALSE, 0, 2, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = 3);
INSERT INTO menu_item (id, optional, prix_ht, menu, taux_tva, restaurant_id)
SELECT 4, FALSE, 0, 1, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = 4);
INSERT INTO menu_item (id, optional, prix_ht, menu, taux_tva, restaurant_id)
SELECT 5, FALSE, 0, 2, 'normal', 1
WHERE NOT EXISTS (SELECT 1 FROM menu_item WHERE id = 5);

-- Association des menu items avec les produits
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 1, 1
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 1 AND produit_id = 1);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 2, 3
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 2 AND produit_id = 3);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 3, 2
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 3 AND produit_id = 2);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 4, 6
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 4 AND produit_id = 6);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 4, 7
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 4 AND produit_id = 7);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 4, 8
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 4 AND produit_id = 8);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 4, 9
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 4 AND produit_id = 9);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 4, 10
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 4 AND produit_id = 10);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 4, 11
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 4 AND produit_id = 11);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 5, 6
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 5 AND produit_id = 6);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 5, 7
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 5 AND produit_id = 7);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 5, 8
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 5 AND produit_id = 8);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 5, 9
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 5 AND produit_id = 9);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 5, 10
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 5 AND produit_id = 10);
INSERT INTO menu_item_contient_produit (menu_item_id, produit_id)
SELECT 5, 11
WHERE NOT EXISTS (SELECT 1 FROM menu_item_contient_produit WHERE menu_item_id = 5 AND produit_id = 11);

-- Insertion des type de paiement
INSERT INTO paiement_type (id, name, is_enable, restaurant_id)
SELECT 1, 'CARTE BANCAIRE', true, 1
WHERE NOT EXISTS (SELECT 1 FROM paiement_type WHERE id = 1);
INSERT INTO paiement_type (id, name, is_enable, restaurant_id)
SELECT 2, 'CHEQUE', true, 1
WHERE NOT EXISTS (SELECT 1 FROM paiement_type WHERE id = 2);
INSERT INTO paiement_type (id, name, is_enable, restaurant_id)
SELECT 3, 'ESPECE', true, 1
WHERE NOT EXISTS (SELECT 1 FROM paiement_type WHERE id = 3);
INSERT INTO paiement_type (id, name, is_enable, restaurant_id)
SELECT 4, 'TICKET RESTAURANT', true, 1
WHERE NOT EXISTS (SELECT 1 FROM paiement_type WHERE id = 4);
