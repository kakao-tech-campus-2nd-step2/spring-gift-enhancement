CREATE TABLE IF NOT EXISTS category (
                                          id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                          name VARCHAR(255) NOT NULL UNIQUE
    );

CREATE TABLE IF NOT EXISTS product (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL,
    price DECIMAL(10, 2) NOT NULL,
    image_url VARCHAR(255),
    category_id BIGINT NOT NULL,
    CONSTRAINT fk_category FOREIGN KEY (category_id) REFERENCES category(id)
    );

CREATE TABLE IF NOT EXISTS members (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL
    );

CREATE TABLE IF NOT EXISTS wishes (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      member_id BIGINT NOT NULL,
                                      product_name VARCHAR(255) NOT NULL,
    CONSTRAINT fk_member FOREIGN KEY (member_id) REFERENCES members(id)
    );
