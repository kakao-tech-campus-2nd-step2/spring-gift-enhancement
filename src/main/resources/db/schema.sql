CREATE TABLE users
(
    user_id  BIGINT AUTO_INCREMENT PRIMARY KEY,
    role     VARCHAR(255) NOT NULL,
    name     VARCHAR(255) NOT NULL,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist
(
    wishlist_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT    NOT NULL,
    created_at  TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users (user_id)
);

CREATE TABLE product
(
    product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    price DOUBLE NOT NULL,
    image_url  VARCHAR(255) NOT NULL
);

CREATE TABLE wishlist_product
(
    wishlist_product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    wishlist_id         BIGINT NOT NULL,
    product_id          BIGINT NOT NULL,
    FOREIGN KEY (wishlist_id) REFERENCES wishlist (wishlist_id),
    FOREIGN KEY (product_id) REFERENCES product (product_id)
);


create table category
(
    color       varchar(7)   not null,
    id          bigint       not null auto_increment,
    description varchar(255),
    image_url   varchar(255) not null,
    name        varchar(255) not null,
    primary key (id)
);
