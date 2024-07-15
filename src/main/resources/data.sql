INSERT INTO category (name) VALUES ('교환권');
INSERT INTO category (name) VALUES ('상품권');
INSERT INTO category (name) VALUES ('뷰티');
INSERT INTO category (name) VALUES ('패션');
INSERT INTO category (name) VALUES ('식품');
INSERT INTO category (name) VALUES ('리빙/도서');
INSERT INTO category (name) VALUES ('레저/스포츠');
INSERT INTO category (name) VALUES ('아티스트/캐릭터');
INSERT INTO category (name) VALUES ('유아동/반려');
INSERT INTO category (name) VALUES ('디지털/가전');
INSERT INTO category (name) VALUES ('카카오프렌즈');
INSERT INTO category (name) VALUES ('트렌드 선물');
INSERT INTO category (name) VALUES ('백화점');

INSERT INTO product (name, price, imageurl, category_id) VALUES ('신라면', 1500, 'https://image.nongshim.com/non/pro/1647822565539.jpg', (SELECT id FROM category WHERE name = '식품'));
INSERT INTO product (name, price, imageurl, category_id) VALUES ('진라면', 1300, 'https://i.namu.wiki/i/GCCJ3FelPiCrZI5kB2Sm_EChm9x1Yv5BI8D63z020UXkVNVs8E3ucCvhiY_97yXAYRr3zd_tpa0rP1deo-gZUA.webp', (SELECT id FROM category WHERE name = '식품'));
INSERT INTO product (name, price, imageurl, category_id) VALUES ('비빔면', 1200, 'https://i.namu.wiki/i/3XSXZdNeUEjycoiU_VcnA3ep1oe7UH7r69YdCDcFkPVvJTQBEY_WWSXpBZ8SDjRcyX3mEQsDmzB0xMof4G89Lg.webp', (SELECT id FROM category WHERE name = '식품'));
INSERT INTO product (name, price, imageurl, category_id) VALUES ('카카오라면', 1100, 'https://i.namu.wiki/i/V3af5EZyVp6iEszBOsFtbdlJixznvd5gd35gmTNNHQCSPCcIdo2qixzNv9LrC_rVF6FTErWMghOFNrLLFExtVw.webp', (SELECT id FROM category WHERE name = '카카오프렌즈'));
INSERT INTO product (name, price, imageurl, category_id) VALUES ('카카라면', 1100, 'https://st.kakaocdn.net/shoppingstore/store/20240528180123_b9a6145839c346e3a34304f14163d941.png', (SELECT id FROM category WHERE name = '카카오프렌즈'));
INSERT INTO product (name, price, imageurl, category_id) VALUES ('카라면', 1100, 'https://t1.kakaocdn.net/thumb/C630x354.fwebp.q100/?fname=https%3A%2F%2Ft1.kakaocdn.net%2Fkakaocorp%2Fkakaocorp%2Fadmin%2Fnews%2F79590191017a00001.jpg', (SELECT id FROM category WHERE name = '카카오프렌즈'));

INSERT INTO member (email, password) VALUES ('user1@example.com', 'password1');
INSERT INTO member (email, password) VALUES ('user2@example.com', 'password2');