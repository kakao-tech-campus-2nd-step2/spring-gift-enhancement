-- user
drop table if exists users CASCADE;
create table users
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    email    varchar(50),
    password varchar(50)
);

-- category
drop table if exists category CASCADE;
create table category
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    name        varchar(255),
    color       varchar(255),
    imageurl    varchar(255),
    description varchar(255)
);
-- category default data
INSERT INTO category (name, color, imageurl, description)
VALUES ('DefaultCategory', '#FFFFFF', '', '');

-- wishlist
drop table if exists wishlist CASCADE;
create table wishlist
(
    id        bigint AUTO_INCREMENT PRIMARY KEY,
    email     varchar(50),
    productId BIGINT
);

-- product
drop table if exists product CASCADE;
create table product
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    name        varchar(255),
    price       int,
    imageurl    varchar(255),
    categoryid  BIGINT,
    wishlist_id BIGINT
);

-- product_wishlist(중간 테이블)
drop table if exists product_wishlist CASCADE;
create table product_wishlist
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    product_id  bigint,
    wishlist_id bigint,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE
);

-- option
drop table if exists option CASCADE;
create table option
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    name     varchar(50),
    quantity int
);
INSERT INTO option (name, quantity)
VALUES ('DefaultOption', 1);

-- product_option(중간 테이블)
drop table if exists product_option CASCADE;
create table product_option
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    product_id  bigint,
    option_id   bigint,
    option_name varchar(50),
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    FOREIGN KEY (option_id) REFERENCES option (id) ON DELETE CASCADE,
    UNIQUE (product_id, option_name)
);
