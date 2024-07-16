-- 상품(PRODUCTS) 스키마
CREATE TABLE IF NOT EXISTS product (
    id        BIGINT auto_increment PRIMARY KEY,
    name      VARCHAR (255) NOT NULL,
    price     INT NOT NULL,
    image_url VARCHAR (255) NOT NULL
);

-- 회원(MEMBERS) 스키마
CREATE TABLE IF NOT EXISTS member (
    id       BIGINT auto_increment PRIMARY KEY,
    email    VARCHAR (255) NOT NULL UNIQUE,
    password VARCHAR (255) NOT NULL
);

-- 위시 리스트(WISHLISTS) 스키마
CREATE TABLE IF NOT EXISTS wish (
    id         BIGINT auto_increment PRIMARY KEY,
    member_id  BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    FOREIGN KEY ( member_id ) REFERENCES member ( id ),
    FOREIGN KEY ( product_id ) REFERENCES product ( id )
);
