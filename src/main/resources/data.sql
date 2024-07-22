-- category 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO category (name, color, img_url, description)
SELECT '교환권', '#6c95d1', 'https://category.png', ''
WHERE NOT EXISTS (
    SELECT 1 FROM category WHERE name = '교환권'
);

-- member 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO member (email, password)
SELECT 'test@example.com', 'password'
WHERE NOT EXISTS (
    SELECT 1 FROM member WHERE email = 'test@example.com'
);

-- product 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO product (price, name, img_url, category_id)
SELECT 100, 'Product Name', 'http://product.jpg', c.id
FROM category c
WHERE c.name = '교환권'
  AND NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'Product Name'
);

-- wish 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO wish (member_id, product_id)
SELECT m.id, p.id
FROM member m, product p
WHERE m.email = 'test@example.com' AND p.name = 'Product Name'
  AND NOT EXISTS (
    SELECT 1 FROM wish WHERE member_id = m.id AND product_id = p.id
);