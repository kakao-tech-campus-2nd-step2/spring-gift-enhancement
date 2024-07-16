-- 예시 상품 데이터
INSERT INTO product (name, price, image_url)
VALUES ('Product1', 1000, 'https://via.placeholder.com/150?text=product1', 1),
       ('Product2', 2000, 'https://via.placeholder.com/150?text=product2', 2),
       ('Product3', 3000, 'https://via.placeholder.com/150?text=product3', 3),
       ('Product4', 4000, 'https://via.placeholder.com/150?text=product4', 4),
       ('Product5', 5000, 'https://via.placeholder.com/150?text=product5', 5),
       ('Product6', 6000, 'https://via.placeholder.com/150?text=product6', 6),
       ('Product7', 7000, 'https://via.placeholder.com/150?text=product7', 7),
       ('Product8', 8000, 'https://via.placeholder.com/150?text=product8', 8),
       ('Product9', 9000, 'https://via.placeholder.com/150?text=product9', 9),
       ('Product10', 10000, 'https://via.placeholder.com/150?text=product10', 10),
       ('Product11', 11000, 'https://via.placeholder.com/150?text=product11', 11);

-- 예시 카테고리 데이터
INSERT INTO category (name)
VALUES ('교환권'),
       ('상품권'),
       ('뷰티'),
       ('식품'),
       ('리빙/도서'),
       ('레저/스포츠'),
       ('아티스트/캐릭터'),
       ('유아동/반려'),
       ('디지털/가전'),
       ('카카오프렌즈'),
       ('트렌드 선물'),
       ('백화점');

-- 예시 회원 데이터
INSERT INTO member (email, password)
VALUES ('cussle@kakao.com', 'admin'),
       ('tester@kakao.com', 'test');

-- 예시 위시 리스트 데이터
INSERT INTO wish (member_id, product_id)
VALUES (1, 1),
       (1, 2),
       (2, 1),
       (2, 2),
       (2, 6),
       (2, 7),
       (2, 10),
       (2, 11);
