-- 카테고리 데이터 삽입
INSERT INTO category (categoryId, name, color, imgUrl) VALUES
('Clothes', 'black', 'https://www.google.com'),
('Foods', 'white', 'https://www.google.com');

-- 상품 데이터 삽입
INSERT INTO products (name, price, imgUrl, categoryId) VALUES
('Product1', 35000, 'https://www.google.com', 1),
('Product2', 28000, 'https://www.google.com', 2);

-- 회원 데이터 삽입
INSERT INTO members (email, password) VALUES
('test@example.com', 'password');