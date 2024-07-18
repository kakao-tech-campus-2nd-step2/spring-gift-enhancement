CREATE TABLE products (
      id INT AUTO_INCREMENT PRIMARY KEY,
      name VARCHAR(255) NOT NULL,
      price INT NOT NULL,
      imgUrl VARCHAR(255) NOT NULL
);

create table category (
                          id bigint not null auto_increment,
                          name varchar(255) not null,
                          color varchar(7) not null,
                          image_url varchar(255) not null,
                          description varchar(255),
                          primary key (id),
                          unique (name)
) engine=InnoDB;

create table product (
                         id bigint not null auto_increment,
                         name varchar(15) not null,
                         image_url varchar(255) not null,
                         price integer not null,
                         category_id bigint not null,
                         primary key (id),
                         foreign key (category_id) references category (id)
) engine=InnoDB;