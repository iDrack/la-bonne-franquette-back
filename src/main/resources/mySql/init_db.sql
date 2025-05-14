-- 1) RESTAURANT + ADMIN USER
INSERT INTO restaurants(id, name, menu_version, tva_enable)
SELECT 1, 'Le Délice Français', 1, TRUE
WHERE NOT EXISTS (SELECT 1 FROM restaurants WHERE id = 1);

INSERT INTO users(username, password, roles, restaurant_id)
SELECT 'admin', '$2a$10$KV8cQULcx8Y2VNyf5lpOPuFdvJ4rzNmRFyDvgWDvhth.eVKAddo1y', 'ROLE_ADMIN', 1
WHERE NOT EXISTS (SELECT 1 FROM users WHERE username = 'admin');

-- 2) CATEGORIES (5 mains, plus sub-categories)
-- Main categories
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 1, 'Burgers',      'category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=1);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 2, 'Salades',      'category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=2);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 3, 'Pizzas',       'category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=3);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 4, 'Desserts',     'category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=4);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 5, 'Boissons',     'category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=5);

-- Sub-categories
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 6,  'Bœuf',        'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=6);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 7,  'Poulet',      'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=7);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 8,  'Végétarien',  'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=8);

INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 9,  'César',       'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=9);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 10, 'Poulet',      'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=10);

INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 11, 'Margherita',  'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=11);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 12, 'Reine',       'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=12);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 13, 'Végétarienne','sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=13);

INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 14, 'Gâteaux',     'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=14);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 15, 'Glaces',      'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=15);

INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 16, 'Eaux',        'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=16);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 17, 'Sodas',       'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=17);
INSERT INTO categories(id, name, category_type, restaurant_id)
SELECT 18, 'Jus',         'sub-category', 1 WHERE NOT EXISTS(SELECT 1 FROM categories WHERE id=18);

-- 3) INGREDIENTS (11)
INSERT INTO ingredients(id, name, restaurant_id) SELECT 1,  'Tomate',      1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=1);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 2,  'Laitue',      1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=2);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 3,  'Fromage',     1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=3);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 4,  'Jambon',      1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=4);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 5,  'Poulet',      1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=5);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 6,  'Bœuf',        1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=6);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 7,  'Saumon',      1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=7);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 8,  'Champignons', 1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=8);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 9,  'Oignons',     1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=9);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 10, 'Maïs',        1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=10);
INSERT INTO ingredients(id, name, restaurant_id) SELECT 11, 'Olives',      1 WHERE NOT EXISTS(SELECT 1 FROM ingredients WHERE id=11);

-- 4) ADDONS (6)
INSERT INTO addons(id, name, prix_ht, restaurant_id) SELECT 1, 'Bacon',    150,   1 WHERE NOT EXISTS(SELECT 1 FROM addons WHERE id=1);
INSERT INTO addons(id, name, prix_ht, restaurant_id) SELECT 2, 'Avocat',   150,   1 WHERE NOT EXISTS(SELECT 1 FROM addons WHERE id=2);
INSERT INTO addons(id, name, prix_ht, restaurant_id) SELECT 3, 'Œuf',      150,   1 WHERE NOT EXISTS(SELECT 1 FROM addons WHERE id=3);
INSERT INTO addons(id, name, prix_ht, restaurant_id) SELECT 4, 'Cornichon',150,   1 WHERE NOT EXISTS(SELECT 1 FROM addons WHERE id=4);
INSERT INTO addons(id, name, prix_ht, restaurant_id) SELECT 5, 'Cheddar',  150,   1 WHERE NOT EXISTS(SELECT 1 FROM addons WHERE id=5);
INSERT INTO addons(id, name, prix_ht, restaurant_id) SELECT 6, 'Ketchup',  100,   1 WHERE NOT EXISTS(SELECT 1 FROM addons WHERE id=6);

-- 5) PRODUCTS (~25)
-- Burgers (sub-cat 6,7,8)
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 1,  'Burger Classique', 1200, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=1);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 2,  'Double Bacon',     1400, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=2);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 3,  'Veggie Burger',    1350, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=3);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 4,  'Chicken Deluxe',   1350, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=4);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 5,  'Double Burger Végétarien',1550, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=5);

-- Salades (sub-cat 9,10)
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 6,  'Salade César',    800,  1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=6);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 7,  'Salade de Chèvre',900,  1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=7);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 8,  'Salade Végé',     950,  1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=8);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 9,  'Salade Poulet',   950,  1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=9);

-- Pizzas (sub-cat 11,12,13)
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 10, 'Pizza Margherita',900, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=10);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 11, 'Pizza Reine',     1000, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=11);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 12, 'Pizza Chorizo',   1100, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=12);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 13, 'Pizza Végé',      1100, 1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=13);

-- Desserts (sub-cat 14,15)
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 14, 'Tarte aux pommes', 600,1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=14);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 15, 'Glace vanille',    850,1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=15);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 16, 'Mousse au chocolat',650,1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=16);

-- Boissons (sub-cat 16,17,18)
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 17, 'Eau plate',    150,   1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=17);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 18, 'Eau gazeuse',  150,   1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=18);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 19, 'Coca',         300,   1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=19);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 20, 'Orangina',     300,   1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=20);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 21, 'Jus de pomme', 200,   1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=21);
INSERT INTO products(id, name, prix_ht, restaurant_id) SELECT 22, 'Jus d''orange',200,   1 WHERE NOT EXISTS(SELECT 1 FROM products WHERE id=22);

-- 6) PRODUCT⇄CATEGORY
-- Burgers → sub-cats 6,7,8
INSERT INTO product_in_category(product_id, category_id)
SELECT 1,6 WHERE NOT EXISTS(SELECT 1 FROM product_in_category WHERE product_id=1 AND category_id=6);
INSERT INTO product_in_category(product_id, category_id) SELECT 2,7 WHERE NOT EXISTS(SELECT 1 FROM product_in_category WHERE product_id=2 AND category_id=7);
INSERT INTO product_in_category(product_id, category_id) SELECT 3,8 WHERE NOT EXISTS(SELECT 1 FROM product_in_category WHERE product_id=3 AND category_id=8);
INSERT INTO product_in_category(product_id, category_id)
SELECT 4,7 WHERE NOT EXISTS(SELECT 1 FROM product_in_category WHERE product_id=4 AND category_id=7);
INSERT INTO product_in_category(product_id, category_id) SELECT 5,8 WHERE NOT EXISTS(SELECT 1 FROM product_in_category WHERE product_id=5 AND category_id=8);

-- Salades → sub-cats 9,10
INSERT INTO product_in_category(product_id, category_id) SELECT 6,9;
INSERT INTO product_in_category(product_id, category_id) SELECT 7,9;
INSERT INTO product_in_category(product_id, category_id) SELECT 8,10;
INSERT INTO product_in_category(product_id, category_id) SELECT 9,10;

-- Pizzas → sub-cats 11-13
INSERT INTO product_in_category(product_id, category_id) SELECT 10,11;
INSERT INTO product_in_category(product_id, category_id) SELECT 11,12;
INSERT INTO product_in_category(product_id, category_id) SELECT 12,12;
INSERT INTO product_in_category(product_id, category_id) SELECT 13,13;

-- Desserts → sub-cats 14-15
INSERT INTO product_in_category(product_id, category_id) SELECT 14,14;
INSERT INTO product_in_category(product_id, category_id) SELECT 15,15;
INSERT INTO product_in_category(product_id, category_id) SELECT 16,15;

-- Boissons → sub-cats 16-18
INSERT INTO product_in_category(product_id, category_id) SELECT 17,16;
INSERT INTO product_in_category(product_id, category_id) SELECT 18,16;
INSERT INTO product_in_category(product_id, category_id) SELECT 19,17;
INSERT INTO product_in_category(product_id, category_id) SELECT 20,17;
INSERT INTO product_in_category(product_id, category_id) SELECT 21,18;
INSERT INTO product_in_category(product_id, category_id) SELECT 22,18;

-- 7) PRODUCT⇄INGREDIENT (≥1 each)
INSERT INTO product_contains_ingredient(product_id, ingredient_id) SELECT 1,1;
INSERT INTO product_contains_ingredient(product_id, ingredient_id) SELECT 1,3;
INSERT INTO product_contains_ingredient(product_id, ingredient_id) SELECT 1,6;
-- … repeat similar for each product …

-- 8) PRODUCT⇄ADDON (≥3 each)
INSERT INTO product_contains_addon(product_id, addon_id) SELECT 1,1;
INSERT INTO product_contains_addon(product_id, addon_id) SELECT 1,2;
INSERT INTO product_contains_addon(product_id, addon_id) SELECT 1,3;
-- … repeat for each product …

-- 9) MENUS (3)
INSERT INTO menus(id, name, prix_ht,  restaurant_id)
SELECT 1, 'Menu du jour',2000, 1 WHERE NOT EXISTS(SELECT 1 FROM menus WHERE id=1);
INSERT INTO menus(id, name, prix_ht, restaurant_id)
SELECT 2, 'Menu Entrée/Plat',2000, 1 WHERE NOT EXISTS(SELECT 1 FROM menus WHERE id=2);
INSERT INTO menus(id, name, prix_ht, restaurant_id)
SELECT 3, 'Menu Entrée/Plat + Dessert',2500, 1 WHERE NOT EXISTS(SELECT 1 FROM menus WHERE id=3);

-- 10) MENU_ITEMS
-- 1) Menu du jour → Chicken Deluxe (4)
INSERT INTO menu_items(id, optional, menu, prix_ht, restaurant_id)
SELECT 1, FALSE, 1,1000, 1 WHERE NOT EXISTS(SELECT 1 FROM menu_items WHERE id=1);
-- 2) Entrée
INSERT INTO menu_items(id, optional, menu, prix_ht, restaurant_id)
SELECT 2, FALSE, 2,1000, 1 WHERE NOT EXISTS(SELECT 1 FROM menu_items WHERE id=2);
-- 3) Plat
INSERT INTO menu_items(id, optional, menu, prix_ht, restaurant_id)
SELECT 3, FALSE, 2,1000, 1 WHERE NOT EXISTS(SELECT 1 FROM menu_items WHERE id=3);
-- 4) Entrée
INSERT INTO menu_items(id, optional, menu, prix_ht, restaurant_id)
SELECT 4, FALSE, 3,1000, 1 WHERE NOT EXISTS(SELECT 1 FROM menu_items WHERE id=4);
-- 5) Plat
INSERT INTO menu_items(id, optional, menu, prix_ht, restaurant_id)
SELECT 5, FALSE, 3,1000, 1 WHERE NOT EXISTS(SELECT 1 FROM menu_items WHERE id=5);
-- 6) Dessert optional
INSERT INTO menu_items(id, optional, menu, prix_ht, restaurant_id)
SELECT 6, TRUE, 3, 1000, 1 WHERE NOT EXISTS(SELECT 1 FROM menu_items WHERE id=6);

-- 11) MENU_ITEM⇄PRODUCT
INSERT INTO menu_item_contains_product(menu_item_id, product_id)
SELECT 1,4 WHERE NOT EXISTS(SELECT 1 FROM menu_item_contains_product WHERE menu_item_id=1 AND product_id=4);
INSERT INTO menu_item_contains_product(menu_item_id, product_id)
SELECT 2,6;
INSERT INTO menu_item_contains_product(menu_item_id, product_id)
SELECT 3,1;
INSERT INTO menu_item_contains_product(menu_item_id, product_id)
SELECT 4,7;
INSERT INTO menu_item_contains_product(menu_item_id, product_id)
SELECT 5,2;
INSERT INTO menu_item_contains_product(menu_item_id, product_id)
SELECT 6,14;

-- 12) PAYMENT TYPE
INSERT INTO payment_type(id, name, is_enable, restaurant_id)
SELECT 1, 'CARTE BANCAIRE',        TRUE, 1 WHERE NOT EXISTS(SELECT 1 FROM payment_type WHERE id=1);
INSERT INTO payment_type(id, name, is_enable, restaurant_id)
SELECT 2, 'CHEQUE',                TRUE, 1 WHERE NOT EXISTS(SELECT 1 FROM payment_type WHERE id=2);
INSERT INTO payment_type(id, name, is_enable, restaurant_id)
SELECT 3, 'ESPECE',                TRUE, 1 WHERE NOT EXISTS(SELECT 1 FROM payment_type WHERE id=3);
INSERT INTO payment_type(id, name, is_enable, restaurant_id)
SELECT 4, 'TICKET RESTAURANT',     TRUE, 1 WHERE NOT EXISTS(SELECT 1 FROM payment_type WHERE id=4);
