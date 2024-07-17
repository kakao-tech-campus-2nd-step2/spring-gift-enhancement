DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS cart;

-- 상품 --
CREATE TABLE product
(
    id        INT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255)   NOT NULL,
    price     DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255)
);

ALTER TABLE product
    ALTER COLUMN id RESTART WITH 1;

-- 유저 --
CREATE TABLE users
(
    id       INT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
);

ALTER TABLE users
    ALTER COLUMN id RESTART WITH 1;

-- 장바구니 --
CREATE TABLE IF NOT EXISTS cart
(
    id
    INT
    AUTO_INCREMENT
    PRIMARY
    KEY,
    user_id
    INT
    NOT
    NULL,
    product_id
    INT
    NOT
    NULL,

    FOREIGN
    KEY
(
    user_id
) REFERENCES users
(
    id
) ON DELETE CASCADE,
    FOREIGN KEY
(
    product_id
) REFERENCES product
(
    id
)
  ON DELETE CASCADE
    );

ALTER TABLE cart
    ALTER COLUMN id RESTART WITH 1;
