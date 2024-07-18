-- 카테고리 데이터 삽입
INSERT INTO category (name, color, imgUrl) VALUES
                                               ('Clothes', 'black', 'https://www.google.com'),
                                               ('Foods', 'white', 'https://www.google.com');

-- 회원 데이터 삽입
INSERT INTO members (email, password) VALUES
                                          ('test1@example.com', 'password1'),
                                          ('test2@example.com', 'password2');

-- 상품 데이터 삽입
INSERT INTO products (name, price, imgUrl, category_id) VALUES
                                                            ('T-shirt', 10000, 'https://www.google.com', 1),
                                                            ('Apple', 2000, 'https://www.google.com', 2);