-- 기존 테이블 삭제
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS wish;
DROP TABLE IF EXISTS category;

-- product 테이블 생성
CREATE TABLE IF NOT EXISTS product (
                                       id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                       name VARCHAR(255) NOT NULL,
                                       price BIGINT NOT NULL,
                                       url VARCHAR(255) NOT NULL
);

-- user 테이블 생성
CREATE TABLE IF NOT EXISTS user (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    email VARCHAR(255) NOT NULL,
                                    password VARCHAR(255) NOT NULL
);

-- wish 테이블 생성
CREATE TABLE IF NOT EXISTS wish (
                                    id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                    product_id BIGINT NOT NULL,
                                    user_id BIGINT NOT NULL,
                                    FOREIGN KEY (product_id) REFERENCES product(id),
                                    FOREIGN KEY (user_id) REFERENCES user(id)
);

-- category 테이블 생성
CREATE TABLE IF NOT EXISTS category (
                                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                                        name VARCHAR(255) NOT NULL,
                                        color VARCHAR(7) NOT NULL,
                                        description VARCHAR(255),
                                        image_url VARCHAR(255) NOT NULL
);