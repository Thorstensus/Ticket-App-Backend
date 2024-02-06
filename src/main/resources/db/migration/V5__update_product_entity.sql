ALTER TABLE products
    ADD COLUMN start_of_sale bigint,
    ADD COLUMN end_of_sale bigint,
    ADD COLUMN price_before_sale double,
    ADD COLUMN is_on_sale bit null;
UPDATE products SET is_on_sale = false WHERE is_on_sale IS NULL;