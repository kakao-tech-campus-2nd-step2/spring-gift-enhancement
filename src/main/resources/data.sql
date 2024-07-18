INSERT INTO category (name, img_url) VALUES
                                         ('전자기기', 'https://example.com/electronics.jpg'),
                                         ('의류', 'https://example.com/clothing.jpg'),
                                         ('도서', 'https://example.com/books.jpg');

INSERT INTO item (name, price, img_url, category_id) VALUES
                                                         ('스마트폰', 1000000, 'https://example.com/smartphone.jpg', 1),
                                                         ('노트북', 1500000, 'https://example.com/laptop.jpg', 1),
                                                         ('무선이어폰', 300000, 'https://example.com/earbuds.jpg', 1),
                                                         ('티셔츠', 30000, 'https://example.com/tshirt.jpg', 2),
                                                         ('청바지', 70000, 'https://example.com/jeans.jpg', 2),
                                                         ('후드티', 50000, 'https://example.com/hoodie.jpg', 2),
                                                         ('소설책', 15000, 'https://example.com/novel.jpg', 3),
                                                         ('자기계발서', 18000, 'https://example.com/selfhelp.jpg', 3),
                                                         ('요리책', 25000, 'https://example.com/cookbook.jpg', 3),
                                                         ('만화책', 12000, 'https://example.com/comic.jpg', 3);

INSERT INTO users (email, password) VALUES
                                        ('user1@example.com', 'password1'),
                                        ('user2@example.com', 'password2'),
                                        ('user3@example.com', 'password3');

INSERT INTO wish (user_id, item_id)
SELECT u.id, i.id
FROM users u
         CROSS JOIN item i
WHERE (
          SELECT COUNT(*)
          FROM wish w
          WHERE w.user_id = u.id
      ) < (FLOOR(RAND() * 3) + 3)
  AND NOT EXISTS (
        SELECT 1
        FROM wish w
        WHERE w.user_id = u.id AND w.item_id = i.id
    )
ORDER BY RAND()
    LIMIT 15;