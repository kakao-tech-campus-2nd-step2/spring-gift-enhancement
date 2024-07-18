DROP TABLE IF EXISTS WISHES;
DROP TABLE IF EXISTS OPTIONS;
DROP TABLE IF EXISTS PRODUCTS;
DROP TABLE IF EXISTS CATEGORIES;
DROP TABLE IF EXISTS MEMBERS;

-- Create table for categories
CREATE TABLE CATEGORIES (
                            ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                            NAME VARCHAR(255) NOT NULL UNIQUE,
                            COLOR VARCHAR(255) NOT NULL,
                            IMAGE_URL VARCHAR(255) NOT NULL,
                            DESCRIPTION VARCHAR(255) NOT NULL
);

-- Create table for products
CREATE TABLE PRODUCTS (
                          ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                          NAME VARCHAR(15) NOT NULL,
                          PRICE INT NOT NULL,
                          IMAGE_URL VARCHAR(255) NOT NULL,
                          CATEGORY_ID BIGINT,
                          CONSTRAINT FK_CATEGORY
                              FOREIGN KEY (CATEGORY_ID)
                                  REFERENCES CATEGORIES (ID)
                                  ON DELETE SET NULL
);

-- Create table for options
CREATE TABLE OPTIONS (
                         ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                         NAME VARCHAR(255) NOT NULL UNIQUE,
                         QUANTITY INT,
                         PRODUCT_ID BIGINT,
                         CONSTRAINT FK_PRODUCT
                             FOREIGN KEY (PRODUCT_ID)
                                 REFERENCES PRODUCTS (ID)
                                 ON DELETE SET NULL
);

-- Create table for members
CREATE TABLE MEMBERS (
                         ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                         EMAIL VARCHAR(255) NOT NULL UNIQUE,
                         PASSWORD VARCHAR(255) NOT NULL
);

-- Create table for wishes
CREATE TABLE WISHES (
                        ID BIGINT AUTO_INCREMENT PRIMARY KEY,
                        MEMBER_ID BIGINT,
                        PRODUCT_ID BIGINT,
                        QUANTITY INT NOT NULL,
                        CONSTRAINT FK_MEMBER
                            FOREIGN KEY (MEMBER_ID)
                                REFERENCES MEMBERS (ID)
                                ON DELETE CASCADE,
                        CONSTRAINT FK_PRODUCT_WISH
                            FOREIGN KEY (PRODUCT_ID)
                                REFERENCES PRODUCTS (ID)
                                ON DELETE CASCADE
);