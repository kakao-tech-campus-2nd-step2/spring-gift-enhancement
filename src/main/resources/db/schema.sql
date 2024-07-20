create table category
(
    color       varchar(7)   not null,
    id          bigint       not null auto_increment,
    description varchar(255),
    image_url   varchar(255) not null,
    name        varchar(255) not null,
    primary key (id)
);

create table product
(
    price       integer      not null,
    category_id bigint       not null,
    id          bigint       not null auto_increment,
    name        varchar(15)  not null,
    image_url   varchar(255) not null,
    primary key (id)
);

CREATE TABLE Users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL
);

CREATE TABLE WishList
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    FOREIGN KEY (user_id) REFERENCES Users (id)
);

CREATE TABLE WishList_Product
(
    wishlist_id BIGINT NOT NULL,
    product_id  BIGINT NOT NULL,
    PRIMARY KEY (wishlist_id, product_id),
    FOREIGN KEY (wishlist_id) REFERENCES WishList (id),
    FOREIGN KEY (product_id) REFERENCES Product (id)
);
