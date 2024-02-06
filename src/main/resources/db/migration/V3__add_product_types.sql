create table product_type
(
    id        bigint auto_increment
        primary key,
    type_name varchar(255) null
);

INSERT INTO product_type (type_name)
VALUES ('Adventure'),
       ('Culinary'),
       ('Cultural'),
       ('Historical');

ALTER TABLE products
    ADD COLUMN product_type_id bigint,
    ADD CONSTRAINT FK_foreign_key_product_type_id
    FOREIGN KEY (product_type_id)
    REFERENCES product_type (id);

ALTER TABLE products
    RENAME COLUMN type TO product_type_name;

UPDATE products
SET product_type_id = 1
WHERE product_type_name = 'Adventure';
UPDATE products
SET product_type_id = 2
WHERE product_type_name = 'Culinary';
UPDATE products
SET product_type_id = 3
WHERE product_type_name = 'Cultural';
UPDATE products
SET product_type_id = 4
WHERE product_type_name = 'Historical';

ALTER TABLE products
DROP
COLUMN product_type_name;
