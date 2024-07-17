INSERT INTO category(name, color, image_url, description)
VALUES ('교환권', '#ffffff', 'https://product-shop.io/one/image.jpg', 'exchange ticket'),
       ('상품권', '#f9f8f7', 'https://product-shop.io/two/image.jpg', 'gift ticket'),
       ('식품', '#f134f2', 'https://product-shop.io/thr/image.jpg', 'food ticket');

INSERT INTO product(name, price, image_url, category_id)
VALUES ('product1', 4500, 'https://shop.io/product/1/image.jpg', 1),
       ('product2', 8000, 'https://shop.io/product/2/image.jpg', 3),
       ('product3', 20000, 'https://shop.io/product/3/image.jpg', 2);

INSERT INTO option(name, quantity, product_id)
VALUES ('옵션1', 100, 1),
       ('옵션2', 5, 1),
       ('옵션1', 20, 2),
       ('옵션2', 3, 2),
       ('옵션1', 60, 3),
       ('옵션2', 1, 3);

INSERT INTO member(email, password)
VALUES ('admin@email.com', 'password'),
       ('testuser@gmail.com', 'user123'),
       ('useruser@naver.com', '1q2w3e4r!@#$');