-- Produits --- 
INSERT INTO produit (id, nom, prix_ht)
SELECT 1, 'Produit1', 550
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 1);
INSERT INTO produit (id, nom, prix_ht)
SELECT 2, 'Produit2', 750
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 2);
INSERT INTO produit (id, nom, prix_ht)
SELECT 3, 'Produit3', 600
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 3);
INSERT INTO produit (id, nom, prix_ht)
SELECT 4, 'Produit4', 600
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 4);
INSERT INTO produit (id, nom, prix_ht)
SELECT 5, 'Produit5', 500
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 5);
INSERT INTO produit (id, nom, prix_ht)
SELECT 6, 'Produit6', 550
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 6);
INSERT INTO produit (id, nom, prix_ht)
SELECT 7, 'Produit7', 550
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 7);
INSERT INTO produit (id, nom, prix_ht)
SELECT 8, 'Produit8', 500
WHERE NOT EXISTS (SELECT 1 FROM produit WHERE id = 8);

-- SousCategorie ---
INSERT INTO sous_categorie (id, nom, categorie_id)
SELECT 1, 'SousCategorie1', 1
WHERE NOT EXISTS (SELECT 1 FROM sous_categorie WHERE id = 1);
INSERT INTO sous_categorie (id, nom, categorie_id)
SELECT 2, 'SousCategorie2', 1
WHERE NOT EXISTS (SELECT 1 FROM sous_categorie WHERE id = 2);
INSERT INTO sous_categorie (id, nom, categorie_id)
SELECT 3, 'SousCategorie3', 2
WHERE NOT EXISTS (SELECT 1 FROM sous_categorie WHERE id = 3);
INSERT INTO sous_categorie (id, nom, categorie_id)
SELECT 4, 'SousCategorie4', 2
WHERE NOT EXISTS (SELECT 1 FROM sous_categorie WHERE id = 4);
INSERT INTO sous_categorie (id, nom, categorie_id)
SELECT 5, 'SousCategorie5', 3
WHERE NOT EXISTS (SELECT 1 FROM sous_categorie WHERE id = 5);

-- Paiement ---
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 1, NOW(), 'CB', true, 500, 550, 1
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 1);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 2, NOW(), 'ESP', true, 300, 330, 1
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 2);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 3, NOW(), 'AUTRE', false, 200, 220, 1
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 3);

INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 4, NOW(), 'CB', false, 1000, 1100, 2
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 4);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 5, NOW(), 'ESP', true, 500, 550, 2
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 5);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 6, NOW(), 'AUTRE', false, 500, 550, 2
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 6);

INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 7, NOW(), 'CB', true, 500, 550, 3
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 7);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 8, NOW(), 'ESP', false, 500, 550, 3
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 8);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 9, NOW(), 'AUTRE', true, 500, 550, 3
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 9);

INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 10, NOW(), 'CB', false, 1000, 1100, 4
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 10);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 11, NOW(), 'ESP', true, 1000, 1100, 4
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 11);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 12, NOW(), 'AUTRE', false, 500, 550, 4
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 12);

INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 13, NOW(), 'CB', true, 1000, 1100, 5
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 13);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 14, NOW(), 'ESP', false, 1000, 1100, 5
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 14);
INSERT INTO paiement (id, date, type, ticket_envoye, prix_ht, prix_ttc, commande_id)
SELECT 15, NOW(), 'AUTRE', true, 1000, 1100, 5
WHERE NOT EXISTS (SELECT 1 FROM paiement WHERE id = 15);

-- Menu ---
INSERT INTO menu (id, nom, prix_ht)
SELECT 1, 'Menu1', 1000
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE id = 1);
INSERT INTO menu (id, nom, prix_ht)
SELECT 2, 'Menu2', 2000
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE id = 2);
INSERT INTO menu (id, nom, prix_ht)
SELECT 3, 'Menu3', 1500
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE id = 3);
INSERT INTO menu (id, nom, prix_ht)
SELECT 4, 'Menu4', 2500
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE id = 4);
INSERT INTO menu (id, nom, prix_ht)
SELECT 5, 'Menu5', 3000
WHERE NOT EXISTS (SELECT 1 FROM menu WHERE id = 5);

-- Ingredient ---
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
SELECT 1, 'Ingredient1', false, 'ingredient'
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 1);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
SELECT 2, 'Ingredient2', true, 'ingredient'
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 2);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
SELECT 3, 'Ingredient3', false, 'ingredient'
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 3);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
SELECT 4, 'Ingredient4', true, 'ingredient'
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 4);
INSERT INTO ingredient (id, nom, a_cuire, ingredient_type)
SELECT 5, 'Ingredient5', false, 'ingredient'
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 5);

-- Extra ---
INSERT INTO extra (id, nom, a_cuire, ingredient_type, prixHT)
SELECT 1, 'Extra1', false, 'extra', 100
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 1);
INSERT INTO extra (id, nom, a_cuire, ingredient_type, prixHT)
SELECT 2, 'Extra2', true, 'extra', 200
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 2);
INSERT INTO extra (id, nom, a_cuire, ingredient_type, prixHT)
SELECT 3, 'Extra3', false, 'extra', 150
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 3);
INSERT INTO extra (id, nom, a_cuire, ingredient_type, prixHT)
SELECT 4, 'Extra4', true, 'extra', 250
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 4);
INSERT INTO extra (id, nom, a_cuire, ingredient_type, prixHT)
SELECT 5, 'Extra5', false, 'extra', 300
WHERE NOT EXISTS (SELECT 1 FROM extra WHERE id = 5);

-- Commande ---
INSERT INTO commande (id, numero, date_saisie, date_livraison, status, sur_place, nb_article, prix_ht)
SELECT 1, 1, NOW(), NOW(), 'PENDING', true, 2, 1000
WHERE NOT EXISTS (SELECT 1 FROM commande WHERE id = 1);
INSERT INTO commande (id, numero, date_saisie, date_livraison, status, sur_place, nb_article, prix_ht)
SELECT 2, 2, NOW(), NOW(), 'COMPLETED', false, 3, 2000
WHERE NOT EXISTS (SELECT 1 FROM commande WHERE id = 2);
INSERT INTO commande (id, numero, date_saisie, date_livraison, status, sur_place, nb_article, prix_ht)
SELECT 3, 3, NOW(), NOW(), 'PENDING', true, 1500
WHERE NOT EXISTS (SELECT 1 FROM commande WHERE id = 3);
INSERT INTO commande (id, numero, date_saisie, date_livraison, status, sur_place, nb_article, prix_ht)
SELECT 4, 4, NOW(), NOW(), 'CANCELLED', false, 2500
WHERE NOT EXISTS (SELECT 1 FROM commande WHERE id = 4);
INSERT INTO commande (id, numero, date_saisie, date_livraison, status, sur_place, nb_article, prix_ht)
SELECT 5, 5, NOW(), NOW(), 'PENDING', true, 3000
WHERE NOT EXISTS (SELECT 1 FROM commande WHERE id = 5);

-- Categorie ---
INSERT INTO categorie (id, nom, categorie_type)
SELECT 1, 'Categorie1', 'categorie'
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 1);
INSERT INTO categorie (id, nom, categorie_type)
SELECT 2, 'Categorie2', 'categorie'
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 2);
INSERT INTO categorie (id, nom, categorie_type)
SELECT 3, 'Categorie3', 'categorie'
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 3);
INSERT INTO categorie (id, nom, categorie_type)
SELECT 4, 'Categorie4', 'categorie'
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 4);
INSERT INTO categorie (id, nom, categorie_type)
SELECT 5, 'Categorie5', 'categorie'
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);