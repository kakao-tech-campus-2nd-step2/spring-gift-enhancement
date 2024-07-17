drop table if exists users CASCADE;
create table users
(
    id       bigint AUTO_INCREMENT PRIMARY KEY,
    email    varchar(50),
    password varchar(50)
);

drop table if exists category CASCADE;
create table category
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    name        varchar(255),
    color       varchar(255),
    imageurl    varchar(255),
    description varchar(255)
);

INSERT INTO category (name, color, imageurl, description)
VALUES ('DefaultCategory', '#FFFFFF', '', '');

drop table if exists wishlist CASCADE;
create table wishlist
(
    id        bigint AUTO_INCREMENT PRIMARY KEY,
    email     varchar(50),
    productId BIGINT
);

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

drop table if exists product_wishlist CASCADE;
create table product_wishlist
(
    id          bigint AUTO_INCREMENT PRIMARY KEY,
    product_id  bigint,
    wishlist_id bigint,
    FOREIGN KEY (product_id) REFERENCES product (id) ON DELETE CASCADE,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist (id) ON DELETE CASCADE
)