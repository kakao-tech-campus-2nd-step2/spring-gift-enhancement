INSERT INTO category (name, color, image_url, description) VALUES ('식품', '#6c95d1', 'www.food.com', '식품입니다.');
INSERT INTO category (name, color, image_url, description) VALUES ('패션', '#7c95d2', 'www.fashion.com', '패션입니다.');
INSERT INTO category (name, color, image_url, description) VALUES ('가전', '#8c95d3', 'www.electiric.com', '가전입니다.');

INSERT INTO products (name, price, image_url, category_id) VALUES ('coffee', 4500, 'www.coffee.com', 1);
INSERT INTO products (name, price, image_url, category_id) VALUES ('cake', 6500, 'www.cake.com', 1);
INSERT INTO products (name, price, image_url, category_id) VALUES ('bread', 3000, 'www.bread.com', 1);
INSERT INTO products (name, price, image_url, category_id) VALUES ('cookie', 2500, 'www.cookie.com', 1);
INSERT INTO products (name, price, image_url, category_id) VALUES ('iceTea', 3000, 'www.iceTea.com', 1);
INSERT INTO products (name, price, image_url, category_id) VALUES ('cledor', 2500, 'www.cledor.com', 1);
INSERT INTO products (name, price, image_url, category_id) VALUES ('y-shirt', 35000, 'www.yshirt.com', 2);
INSERT INTO products (name, price, image_url, category_id) VALUES ('TV', 600000, 'www.tv.com', 3);

INSERT INTO users (email, password, role) VALUES ('admin@admin', 'admin', 'admin');
INSERT INTO users (email, password, role) VALUES ('user@user', 'user', 'user');

INSERT INTO wish (user_id, product_id) VALUES (2, 1);
INSERT INTO wish (user_id, product_id) VALUES (2, 2);
INSERT INTO wish (user_id, product_id) VALUES (2, 3);
INSERT INTO wish (user_id, product_id) VALUES (2, 4);