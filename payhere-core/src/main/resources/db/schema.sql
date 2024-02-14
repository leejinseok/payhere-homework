drop table if exists product;
drop table if exists shop_owner;

create table product
(
    id               bigint not null auto_increment,
    cost_price       decimal(38, 2),
    price            decimal(38, 2),
    barcode          varchar(255),
    category         varchar(255),
    description      varchar(255),
    name             varchar(255),
    size             varchar(255),
    expiry_date      date,
    created_at       datetime(6),
    last_modified_at datetime(6),
    primary key (id)
) engine=InnoDB;

create table shop_owner
(
    id               bigint not null auto_increment,
    phone_number     varchar(11),
    password         varchar(255),
    created_at       datetime(6),
    last_modified_at datetime(6),
    primary key (id)
) engine=InnoDB;