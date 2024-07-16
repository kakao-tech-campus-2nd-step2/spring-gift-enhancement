create table category
(
    id bigint not null auto_increment,
    name varchar(255) not null,
    color varchar(7),
    image_url varchar(255),
    description varchar(255),
    primary key (id)
) engine=InnoDB;

create table product
(
    id bigint not null auto_increment,
    name varchar(15) not null,
    price bigint not null,
    image_url varchar(255) not null,
    category_id bigint not null,
    primary key (id),
    foreign key (category_id) references category (id)
) engine=InnoDB;

alter table category
    add constraint uk_category unique (name);
