-- 테이블이 존재할 경우 삭제
DROP TABLE IF EXISTS wishlists;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS product_options;

-- 회원 테이블 생성
CREATE TABLE members (
                         member_id BIGINT auto_increment PRIMARY KEY COMMENT '회원 id',
                         email VARCHAR(255) NOT NULL UNIQUE COMMENT '회원 이메일',
                         password VARCHAR(255) NOT NULL COMMENT '회원 비밀번호'
);

-- 카테고리 테이블 생성
CREATE TABLE category (
                          category_id BIGINT auto_increment PRIMARY KEY COMMENT '카테고리 종류',
                          name VARCHAR(255) NOT NULL COMMENT '카테고리 종류',
                          color VARCHAR(255) COMMENT '카테고리 색상',
                          img_url VARCHAR(255) COMMENT '카테고리 이미지 URL'
);

-- 상품 테이블 생성
CREATE TABLE products (
                          product_id BIGINT auto_increment PRIMARY KEY COMMENT '상품 id',
                          name VARCHAR(15) NOT NULL COMMENT '상품 이름',
                          price INTEGER NOT NULL COMMENT '상품 가격',
                          img_url VARCHAR(255) NOT NULL COMMENT '상품 이미지 url',
                          category_id BIGINT NOT NULL COMMENT '카테고리 id',
                          FOREIGN KEY (category_id) REFERENCES category(category_id)
);

-- 옵션 테이블 생성
CREATE TABLE product_options (
                                 option_id BIGINT auto_increment PRIMARY KEY COMMENT '옵션 id',
                                 product_id BIGINT NOT NULL COMMENT '상품 id',
                                 option_name VARCHAR(50) NOT NULL COMMENT '옵션 이름',
                                 quantity INTEGER NOT NULL CHECK (quantity >= 1 AND quantity < 100000000) COMMENT '옵션 수량',
                                 FOREIGN KEY (product_id) REFERENCES products(product_id),
                                 UNIQUE (product_id, option_name)
);

-- 위시리스트 테이블 생성
CREATE TABLE wishlists (
                           wishlist_id BIGINT auto_increment PRIMARY KEY COMMENT '위시리스트 id',
                           member_id BIGINT NOT NULL COMMENT '회원 id',
                           product_id BIGINT NOT NULL COMMENT '상품 id',
                           FOREIGN KEY (member_id) REFERENCES members(member_id),
                           FOREIGN KEY (product_id) REFERENCES products(product_id)
);