INSERT INTO category (name)
VALUES ('교환권'),
       ('백화점');

INSERT INTO product (name, price, category_id, image_url)
VALUES ('예시1', '100', 1, '예시1 Image Url'),
       ('예시2', '200', 2, '예시2 Image Url');

INSERT INTO member (name, email, password, role)
VALUES ('관리자', 'admin@email.com', 'password', 'admin');
INSERT INTO member (name, email, password, role)
VALUES ('사용자', 'member@email.com', 'password', 'user');
INSERT INTO member (name, email, password, role)
VALUES ('카카오', 'kakaouser@email.com', 'password', 'kakaouser');