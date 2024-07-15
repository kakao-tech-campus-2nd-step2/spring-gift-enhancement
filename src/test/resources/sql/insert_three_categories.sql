DELETE FROM categories;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 1;

INSERT INTO categories (name) VALUES ('Category 1');
INSERT INTO categories (name) VALUES ('Category 2');
INSERT INTO categories (name) VALUES ('Category 3');
