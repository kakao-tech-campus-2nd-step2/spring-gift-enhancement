use kakao_gift;

INSERT INTO CATEGORY(name) VALUES ('교환권');
INSERT INTO CATEGORY(name) VALUES ('상품권');
INSERT INTO CATEGORY(name) VALUES ('뷰티');
INSERT INTO CATEGORY(name) VALUES ('패션');
INSERT INTO CATEGORY(name) VALUES ('식품');

INSERT INTO PRODUCT(category_id, name, price, image_url) VALUES (1, '아이스 카페 아메리카노 T', 4500, 'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg');
INSERT INTO PRODUCT(category_id, name, price, image_url) VALUES (1, '아이스 카페 라떼 T', 5000, 'https://some/cafe/latte/image');

INSERT INTO MEMBER(email, password, role) VALUES ('kakao@kakao.com', 'helloKakao12', 'ADMIN');
INSERT INTO MEMBER(email, password, role) VALUES ('test@test.com', 'Tester789', 'USER');

INSERT INTO WISH(member_id, product_id, quantity) VALUES (1, 1, 3);