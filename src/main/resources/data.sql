-- category 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO category (name, color, image_url, description)
SELECT '교환권', '#6c95d1', 'https://category.png', 'detail'
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
INSERT INTO product (price, name, image_url, category_id)
SELECT 100, 'Product Name', 'http://product.jpg', c.id
FROM category c
WHERE c.name = '교환권'
  AND NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'Product Name'
);

-- option 테이블에 데이터 삽입
INSERT INTO option (name, quantity, product_id)
SELECT '01. [Best] 시어버터 핸드 & 시어 스틱 립 밤', 969, p.id
FROM product p
WHERE p.name = 'Product Name'
  AND NOT EXISTS (
    SELECT 1 FROM option WHERE name = '01. [Best] 시어버터 핸드 & 시어 스틱 립 밤' AND product_id = p.id
);

-- wish 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO wish (member_id, product_id)
SELECT m.id, p.id
FROM member m, product p
WHERE m.email = 'test@example.com' AND p.name = 'Product Name'
  AND NOT EXISTS (
    SELECT 1 FROM wish WHERE member_id = m.id AND product_id = p.id
);