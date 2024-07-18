INSERT INTO CATEGORY(name, color, image_url, description) VALUES ('교환권', '#6c95d1', 'https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png', '');
INSERT INTO CATEGORY(name, color, image_url, description) VALUES ('상품권', '#8fcc5d', 'https://any-image.net/giftcard.png', '');
INSERT INTO CATEGORY(name, color, image_url, description) VALUES ('뷰티', '#e09edc', 'https://any-image.net/beauty.png', '');
INSERT INTO CATEGORY(name, color, image_url, description) VALUES ('패션', '#304b99', 'https://any-image.net/fashion.png', '');
INSERT INTO CATEGORY(name, color, image_url, description) VALUES ('식품', '#42bf3c', 'https://any-image.net/food.png', '');

INSERT INTO PRODUCT(category_id, name, price, image_url) VALUES (1, '아이스 카페 아메리카노 T', 4500, 'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg');
INSERT INTO PRODUCT(category_id, name, price, image_url) VALUES (1, '아이스 카페 라떼 T', 5000, 'https://some/cafe/latte/image');

INSERT INTO MEMBER(email, password, role) VALUES ('kakao@kakao.com', 'helloKakao12', 'ADMIN');
INSERT INTO MEMBER(email, password, role) VALUES ('test@test.com', 'Tester789', 'USER');

INSERT INTO WISH(member_id, product_id, quantity) VALUES (1, 1, 3);