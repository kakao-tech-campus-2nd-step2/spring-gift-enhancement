-- 데이터 삽입 전에 기존 데이터 삭제
DELETE FROM wish;
DELETE FROM product;
DELETE FROM category;
DELETE FROM member;

-- Category 데이터 추가
INSERT INTO category (name, color, img_url, description)
VALUES ('교환권', '#6c95d1', 'https://category.png', 'detail');

-- Product 데이터 추가
INSERT INTO product (name, img_url, price, category_id)
VALUES ('Sample Product', 'https://product.png', 1000.0, (SELECT id FROM category WHERE name = '교환권'));

-- Member 데이터 추가
INSERT INTO member (email, password)
VALUES ('test@example.com', 'password');

-- Wish 데이터 추가
INSERT INTO wish (member_id, product_id)
VALUES ((SELECT id FROM member WHERE email = 'test@example.com'), (SELECT id FROM product WHERE name = 'Sample Product'));
