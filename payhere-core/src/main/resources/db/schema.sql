drop table if exists product;
drop table if exists shop_owner;

create table shop_owner
(
    id               bigint not null auto_increment,
    phone_number     varchar(11) not null,
    password         varchar(255) not null,
    created_at       datetime(6) not null,
    last_modified_at datetime(6) not null,
    unique key shop_owner_unique_1 (phone_number),
    primary key (id)
) engine=InnoDB;

create table product
(
    id               bigint not null auto_increment,
    cost_price       decimal(38, 2) not null,
    price            decimal(38, 2) not null,
    barcode          varchar(255) not null,
    category         varchar(255) not null,
    description      varchar(255) not null,
    name             varchar(255) not null,
    size             varchar(255) not null,
    expiry_date      date not null,
    shop_owner_id    bigint not null,
    created_at       datetime(6) not null,
    last_modified_at datetime(6) not null,
    unique key product_unique_1 (barcode),
    constraint product_fk_1 foreign key (shop_owner_id) references shop_owner (id),
    primary key (id)
) engine=InnoDB;