-- 더미 데이터를 삽입할 sql파일

insert into products (product_id, name, price, image)
values (default, 'dummy1', 10000, 'dummy1.png');
insert into products (product_id, name, price, image)
values (default, 'dummy2', 20000, 'dummy2.png');
insert into products (product_id, name, price, image)
values (default, 'dummy3', 30000, 'dummy3.png');

insert into users (user_id, email, password, is_admin)
values (default, 'luckyrkd@naver.com', 'aaaaa11111', true);
insert into users (user_id, email, password, is_admin)
values (default, 'kangji0615@gmail.com', 'aaaaa11111', false);