-- 카테고리 데이터 삽입
INSERT INTO category (name) VALUES ('Electronics'), ('Books');

-- 상품 데이터 삽입
INSERT INTO products (name, price, imgUrl, categoryId) VALUES
('Laptop', 1000, 'https://www.google.com', 1),
('Novel', 20, 'https://www.google.com', 2);

-- 회원 데이터 삽입
INSERT INTO members (email, password) VALUES
('user1@example.com', 'password1'),
('user2@example.com', 'password2');

-- 위시리스트 데이터 삽입
INSERT INTO wishLists (memberId, productId) VALUES
(1, 1),
(2, 2);