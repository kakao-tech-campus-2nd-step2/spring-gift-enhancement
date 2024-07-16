-- Inserting initial data for products
INSERT INTO category (name) VALUES ('test1');
INSERT INTO category (name) VALUES ('test2');
INSERT INTO category (name) VALUES ('test3');

-- Inserting initial data for products
INSERT INTO product (name, price, image_url,category_id)
VALUES ('Product A', 1000, 'image1.jpg',1);
INSERT INTO product (name, price, image_url,category_id)
VALUES ('Product B', 1500, 'image2.jpg',2);
INSERT INTO product (name, price, image_url,category_id)
VALUES ('Product C', 2000, 'image3.jpg',3);
INSERT INTO Product (name, price, image_url,category_id)
VALUES ('커피', 100,
        'https://st.kakaocdn.net/product/gift/product/20231010111814_9a667f9eccc943648797925498bdd8a3.jpg',1);
INSERT INTO Product (name, price, image_url,category_id)
VALUES ('콜라', 100,
        'https://img.danawa.com/prod_img/500000/059/749/img/13749059_1.jpg?_v=20220524145210',2);
INSERT INTO Product (name, price, image_url,category_id)
VALUES ('몬스터', 200,
        'https://img.danawa.com/prod_img/500000/658/896/img/17896658_1.jpg?_v=20220923092758',3);


-- Wish 테이블에 데이터 삽입
INSERT INTO Wish (product_id, user_id)
VALUES (1, 1);
INSERT INTO Wish (product_id, user_id)
VALUES (2, 1);
INSERT INTO Wish (product_id, user_id)
VALUES (1, 2);
INSERT INTO Wish (product_id, user_id)
VALUES (3, 2);
