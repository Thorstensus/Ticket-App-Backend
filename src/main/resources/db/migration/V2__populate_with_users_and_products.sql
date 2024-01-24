INSERT INTO users (name, email, password, role, is_verified)
VALUES
    ('User1', 'user1@example.com', SHA2('password1', 256), 'USER', true),
    ('User2', 'user2@example.com', SHA2('password2', 256), 'USER', true),
    ('User3', 'user3@example.com', SHA2('password3', 256), 'USER', true),
    ('User4', 'user4@example.com', SHA2('password4', 256), 'USER', true),
    ('User5', 'user5@example.com', SHA2('password5', 256), 'USER', true),
    ('User6', 'user6@example.com', SHA2('password6', 256), 'USER', true),
    ('User7', 'user7@example.com', SHA2('password7', 256), 'USER', true),
    ('User8', 'user8@example.com', SHA2('password8', 256), 'USER', true),
    ('User9', 'user9@example.com', SHA2('password9', 256), 'USER', true),
    ('User10', 'user10@example.com', SHA2('password10', 256), 'USER', true);


INSERT INTO products (name, price, duration, description, type)
VALUES
    ('Product1', 29.99, 60, 'Description for Product1', 'Cultural'),
    ('Product2', 19.99, 30, 'Description for Product2', 'Adventure'),
    ('Product3', 39.99, 45, 'Description for Product3', 'Culinary'),
    ('Product4', 49.99, 60, 'Description for Product4', 'Historical'),
    ('Product5', 24.99, 30, 'Description for Product5', 'Cultural'),
    ('Product6', 34.99, 45, 'Description for Product6', 'Adventure'),
    ('Product7', 44.99, 60, 'Description for Product7', 'Culinary'),
    ('Product8', 54.99, 30, 'Description for Product8', 'Historical'),
    ('Product9', 28.99, 45, 'Description for Product9', 'Cultural'),
    ('Product10', 38.99, 60, 'Description for Product10', 'Adventure');

