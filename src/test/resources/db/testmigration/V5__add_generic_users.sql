INSERT INTO users (name, email, password, is_verified, role)
VALUES ('1userNotVerified', '1userNotVerified@example.com', 'password123', 0, 'USER'),
       ('2userVerified', '2userVerified@example.com', 'password123', 1, 'USER'),
       ('3adminNotVerified', '3adminNotVerified@example.com', 'password123', 0, 'ADMIN'),
       ('4adminVerified', '4adminVerified@example.com', 'password123', 1, 'ADMIN');
