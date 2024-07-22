CREATE TABLE IF NOT EXISTS member (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      email VARCHAR(255) NOT NULL UNIQUE,
                                      password VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS category (
                                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                        name VARCHAR(255) NOT NULL UNIQUE,
                                        color VARCHAR(7) NOT NULL,
                                        img_url VARCHAR(255) NOT NULL,
                                        description VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                       price INTEGER NOT NULL,
                                       name VARCHAR(15) NOT NULL,
                                       img_url VARCHAR(255) NOT NULL,
                                       category_id BIGINT NOT NULL,
                                       CONSTRAINT fk_product_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS wish (
                                    id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                    member_id BIGINT NOT NULL,
                                    product_id BIGINT NOT NULL,
                                    CONSTRAINT fk_wish_member FOREIGN KEY (member_id) REFERENCES member(id),
                                    CONSTRAINT fk_wish_product FOREIGN KEY (product_id) REFERENCES product(id)
);
