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
INSERT INTO ingredient (nom, a_cuire, ingredient_type, prixHT, ingredient_id)
SELECT 'Extra1', false, 'extra', 100, 1
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 1);
INSERT INTO ingredient (nom, a_cuire, ingredient_type, prixHT, ingredient_id)
SELECT 'Extra2', true, 'extra', 200, 2
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 2);
INSERT INTO ingredient (nom, a_cuire, ingredient_type, prixHT, ingredient_id)
SELECT 'Extra3', false, 'extra', 150, 3
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 3);
INSERT INTO ingredient (nom, a_cuire, ingredient_type, prixHT, ingredient_id)
SELECT 'Extra4', true, 'extra', 250, 4
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 4);
INSERT INTO ingredient (nom, a_cuire, ingredient_type, prixHT, ingredient_id)
SELECT 'Extra5', false, 'extra', 300, 5
WHERE NOT EXISTS (SELECT 1 FROM ingredient WHERE id = 5);


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

-- Sous-Categorie ---
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie1', 'sous-categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 1);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie2', 'sous-categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 2);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie3', 'sous-categorie', 1
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 3);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie4', 'sous-categorie', 2
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 4);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie5', 'sous-categorie', 3
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie6', 'sous-categorie', 3
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie7', 'sous-categorie', 3
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie8', 'sous-categorie', 4
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie9', 'sous-categorie', 5
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);
INSERT INTO categorie (nom, categorie_type, categorie_id)
SELECT 'SousCategorie10', 'sous-categorie', 5
WHERE NOT EXISTS (SELECT 1 FROM categorie WHERE id = 5);