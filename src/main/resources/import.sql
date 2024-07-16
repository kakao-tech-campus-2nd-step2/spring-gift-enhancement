insert into category(name, color, image_url, description) values('상품권', 'red', 'https://st.kakaocdn.net/category1.jpg', '상품권 카테고리입니다.')
insert into category(name, color, image_url, description) values('패션', 'blue', 'https://st.kakaocdn.net/category2.jpg', '패션 카테고리입니다.')
insert into category(name, color, image_url, description) values('뷰티', 'green', 'https://st.kakaocdn.net/category3.jpg', '뷰티 카테고리입니다.')

insert into product(name, price, image_url, category_id) values ('product1', 1000, 'https://st.kakaocdn.net/product1.jpg', 1)
insert into product(name, price, image_url, category_id) values ('product2', 2000, 'https://st.kakaocdn.net/product2.jpg', 2)
insert into product(name, price, image_url, category_id) values ('product3', 3000, 'https://st.kakaocdn.net/product3.jpg', 3)
insert into product(name, price, image_url, category_id) values ('product4', 3000, 'https://st.kakaocdn.net/product4.jpg', 1)
insert into product(name, price, image_url, category_id) values ('product5', 3000, 'https://st.kakaocdn.net/product5.jpg', 2)
insert into product(name, price, image_url, category_id) values ('product6', 3000, 'https://st.kakaocdn.net/product6.jpg', 3)

insert into users(password, email) values ('yso3865', 'yso829612@gmail.com')

insert into wish(user_id, product_id, count) values(1, 1, 3)
insert into wish(user_id, product_id, count) values(1, 2, 3)
insert into wish(user_id, product_id, count) values(1, 3, 3)
insert into wish(user_id, product_id, count) values(1, 4, 3)

