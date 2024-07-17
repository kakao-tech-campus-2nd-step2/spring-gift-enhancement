insert into member(email, password, role) values ('admin', '1234', 'ADMIN');

insert into category(name, color, image_url, description) values('상품권','#6c95d1','https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png','');
insert into category(name, color, image_url, description) values('인형','#a31ad2','https://gift-s.kakaocdn.net/dn/gift/images/m640/dimm_theme.png','');

insert into product(category_id, name, price, image_url) values(1, 'lion', 1000, 'https://i.namu.wiki/i/OgK0X2DAdO0K6vIBGUDvE8fR2jP0dmTu3z6mAM0JVGw310C7H3c9DmsQ_SyBc-s835u5kwHxVpe0HutSHIqo7Q.webp');
insert into product(category_id, name, price, image_url) values(1, 'appeach', 2000, 'https://i.namu.wiki/i/OgK0X2DAdO0K6vIBGUDvE8fR2jP0dmTu3z6mAM0JVGw310C7H3c9DmsQ_SyBc-s835u5kwHxVpe0HutSHIqo7Q.webp');
insert into product(category_id, name, price, image_url) values(1, 'chunsik', 3000, 'https://i.namu.wiki/i/OgK0X2DAdO0K6vIBGUDvE8fR2jP0dmTu3z6mAM0JVGw310C7H3c9DmsQ_SyBc-s835u5kwHxVpe0HutSHIqo7Q.webp');
insert into product(category_id, name, price, image_url) values(1, 'jay_z', 1, 'https://i.namu.wiki/i/OgK0X2DAdO0K6vIBGUDvE8fR2jP0dmTu3z6mAM0JVGw310C7H3c9DmsQ_SyBc-s835u5kwHxVpe0HutSHIqo7Q.webp');

insert into wish(member_id, product_id) values(1,1);
insert into wish(member_id, product_id) values(1,2);
insert into wish(member_id, product_id) values(1,3);
insert into wish(member_id, product_id) values(1,4);