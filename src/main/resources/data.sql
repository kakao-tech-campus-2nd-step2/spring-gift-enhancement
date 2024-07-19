-- 외래 키 제약 조건 비활성화
SET REFERENTIAL_INTEGRITY FALSE;

-- 기존 데이터 삭제
DELETE FROM wishlist;
DELETE FROM products;
DELETE FROM users;
DELETE FROM categories;

-- 시퀀스 재설정
ALTER TABLE products ALTER COLUMN id RESTART WITH 1;
ALTER TABLE users ALTER COLUMN id RESTART WITH 1;
ALTER TABLE categories ALTER COLUMN id RESTART WITH 1;

-- 새로운 카테고리 데이터 삽입
INSERT INTO categories (id, name, color, image_url, description) VALUES
                                                                     (1, '교환권', '#6c95d1', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png', ''),
                                                                     (2, '상품권', '#ff5733', 'https://example.com/image1.png', ''),
                                                                     (3, '뷰티', '#33ff57', 'https://example.com/image2.png', ''),
                                                                     (4, '패션', '#5733ff', 'https://example.com/image3.png', '');

-- 새로운 사용자 데이터 삽입
INSERT INTO users (id, email, password) VALUES
    (1, 'test@example.com', 'password123');

-- 새로운 제품 데이터 삽입
INSERT INTO products (id, name, price, image_url, category_id) VALUES
    (1, 'Sample Product', 1000, 'https://via.placeholder.com/150', 1);

-- 새로운 위시리스트 데이터 삽입
INSERT INTO wishlist (user_id, product_id)
SELECT u.id, p.id
FROM users u, products p
WHERE u.email = 'test@example.com' AND p.name = 'Sample Product';

-- 외래 키 제약 조건 활성화
SET REFERENTIAL_INTEGRITY TRUE;