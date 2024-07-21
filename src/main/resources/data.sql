-- member 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO member (email, password)
SELECT 'test@example.com', 'password'
WHERE NOT EXISTS (
    SELECT 1 FROM member WHERE email = 'test@example.com'
);

-- product 테이블에 데이터 삽입 전에 존재 여부 확인
INSERT INTO product (price, name, img_url)
SELECT 100, 'Product Name', 'http://example.com/product.jpg'
WHERE NOT EXISTS (
    SELECT 1 FROM product WHERE name = 'Product Name'
);