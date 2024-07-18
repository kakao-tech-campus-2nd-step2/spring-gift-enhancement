-- 테이블이 존재할 경우 삭제
DROP TABLE IF EXISTS wishlists;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS category;

-- 회원 테이블 생성
CREATE TABLE members (
                         memberId BIGINT auto_increment PRIMARY KEY COMMENT '회원 id',
                         email VARCHAR(255) NOT NULL UNIQUE COMMENT '회원 이메일',
                         password VARCHAR(255) NOT NULL COMMENT '회원 비밀번호'
);

-- 카테고리 테이블 생성
CREATE TABLE category (
                          categoryId BIGINT auto_increment PRIMARY KEY COMMENT '카테고리 종류',
                          name VARCHAR(255) NOT NULL COMMENT '카테고리 종류',
                          color VARCHAR(255) COMMENT '카테고리 색상',
                          imgUrl VARCHAR(255) COMMENT '카테고리 이미지 URL'
);

-- 상품 테이블 생성
CREATE TABLE products (
                          productId BIGINT auto_increment PRIMARY KEY COMMENT '상품 id',
                          name VARCHAR(15) NOT NULL COMMENT '상품 이름',
                          price INTEGER NOT NULL COMMENT '상품 가격',
                          imgUrl VARCHAR(255) NOT NULL COMMENT '상품 이미지 url',
                          category_id BIGINT NOT NULL COMMENT '카테고리 id',
                          FOREIGN KEY (categoryId) REFERENCES category(categoryId)
);

-- 위시리스트 테이블 생성
CREATE TABLE wishlists (
                           id BIGINT auto_increment PRIMARY KEY COMMENT '위시리스트 id',
                           memberId BIGINT NOT NULL COMMENT '회원 id',
                           productId BIGINT NOT NULL COMMENT '상품 id',
                           FOREIGN KEY (memberId) REFERENCES members(memberId),
                           FOREIGN KEY (productId) REFERENCES products(productId)
);