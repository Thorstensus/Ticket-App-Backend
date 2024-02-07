INSERT INTO users (name, email, password, is_verified, role)
VALUES ('5userWithCart', '5userWithCart@example.com', 'password123', 1, 'USER'),
       ('6userWithNoCart', '6userWithNoCart@example.com', 'password123', 1, 'USER');

INSERT INTO carts (user_id, last_activity)
VALUES (5, CURRENT_DATE);

INSERT INTO products (name, price, duration, description, product_type_id, is_on_sale)
VALUES ('Product11', 55.0, 110, 'Product 11 Product 11 Product 11 Product 11', 1, false),
       ('Product12', 60.0, 120, 'Product 12 Product 12 Product 12 Product 12', 1, false),
       ('Product13', 65.0, 130, 'Product 13 Product 13 Product 13 Product 13', 1, false),
       ('Product14', 70.0, 140, 'Product 14 Product 14 Product 14 Product 14', 2, false),
       ('Product15', 75.0, 150, 'Product 15 Product 15 Product 15 Product 15', 2, false);

INSERT INTO cart_products (quantity, cart_id, product_id)
VALUES (1, 1, 11),
       (2, 1, 12),
       (3, 1, 13),
       (4, 1, 14),
       (5, 1, 15);
