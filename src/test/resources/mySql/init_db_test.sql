
CREATE DATABASE IF NOT EXISTS `franquette-bdd-test`;

-- Produits --- 
INSERT INTO produit (id, nom, prix_ht)
SELECT 1, 'Classic Cheeseburger', 550
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 1);
INSERT INTO produit (id, nom, prix_ht)
SELECT 2, 'Double Bacon Burger', 750
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 2);
INSERT INTO produit (id, nom, prix_ht)
SELECT 3, 'Chicken Deluxe', 600
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 3);
INSERT INTO produit (id, nom, prix_ht)
SELECT 4, 'Veggie Burger', 600
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 4);
INSERT INTO produit (id, nom, prix_ht)
SELECT 5, 'Chicken Caesar Wrap', 500
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 5);
INSERT INTO produit (id, nom, prix_ht)
SELECT 6, 'Spicy Beef Wrap', 550
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 6);
INSERT INTO produit (id, nom, prix_ht)
SELECT 7, 'Caesar Salad', 550
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 7);
INSERT INTO produit (id, nom, prix_ht)
SELECT 8, 'Greek Salad', 500
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 8);