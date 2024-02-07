INSERT INTO users (name, email, password, is_verified, role)
VALUES ('7userWithOrder', '7userWithOrder@example.com', 'password123', 1, 'USER'),
       ('8userWithNoOrder', '8userWithNoOrder@example.com', 'password123', 1, 'USER');

INSERT INTO products (name, price, duration, description, product_type_id, is_on_sale)
VALUES ('Product16', 80.0, 160, 'Product 16 Product 16 Product 16 Product 16', 2, false),
       ('Product17', 85.0, 170, 'Product 17 Product 17 Product 17 Product 17', 2, false),
       ('Product18', 90.0, 180, 'Product 18 Product 18 Product 18 Product 18', 3, false),
       ('Product19', 95.0, 190, 'Product 19 Product 19 Product 19 Product 19', 3, false),
       ('Product20', 100.0, 200, 'Product 20 Product 20 Product 20 Product 20', 3, false);

INSERT INTO orders (expiry, status, user_id)
VALUES ('expiry', 'status', 7);

INSERT INTO order_products (quantity, order_id, product_id)
VALUES (1, 1, 16),
       (2, 1, 17),
       (3, 1, 18),
       (4, 1, 19),
       (5, 1, 20);
