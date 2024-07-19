DROP TABLE IF EXISTS wishes;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS category;


CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE category (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                          name VARCHAR(255) NOT NULL,
                          color VARCHAR(7) NOT NULL,
                          image_url VARCHAR(255) NOT NULL,
                          description VARCHAR(255)
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price BIGINT NOT NULL,
                         description VARCHAR(255),
                         image_url VARCHAR(255) NOT NULL,
                         category_id BIGINT NOT NULL,
                         CONSTRAINT fk_product_category FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE wishes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        amount INT NOT NULL,
                        is_deleted BOOLEAN NOT NULL,
                        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                        CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
);

CREATE TABLE product_option (
                                id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                name VARCHAR(50) NOT NULL,
                                quantity BIGINT NOT NULL,
                                product_id BIGINT NOT NULL,
                                CONSTRAINT fk_product_option_product FOREIGN KEY (product_id) REFERENCES product(id),
                                UNIQUE (product_id, name)
);

/*
DROP TABLE IF EXISTS wishes;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS users;


CREATE TABLE users (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       email VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL
);

CREATE TABLE product (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         price BIGINT NOT NULL,
                         description VARCHAR(255),
                         image_url VARCHAR(255)
);


CREATE TABLE wishes (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT NOT NULL,
                        product_id BIGINT NOT NULL,
                        amount INT NOT NULL,
                        is_deleted BOOLEAN NOT NULL,
                        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id),
                        CONSTRAINT fk_product FOREIGN KEY (product_id) REFERENCES product(id)
);



 */
