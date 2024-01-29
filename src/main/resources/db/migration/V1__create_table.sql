CREATE TABLE news
(
    id           bigint auto_increment
        primary key,
    content      varchar(255) null,
    publish_date date null,
    title        varchar(255) null
);

CREATE TABLE products
(
    id          bigint auto_increment
        primary key,
    description varchar(255) null,
    duration    int null,
    name        varchar(255) null,
    price double null,
    type        enum ('Cultural', 'Adventure', 'Culinary', 'Historical') null
);

CREATE TABLE users
(
    id          bigint auto_increment
        primary key,
    email       varchar(255) null,
    is_verified bit null,
    name        varchar(255) null,
    password    varchar(255) null,
    role        enum ('USER', 'ADMIN') null
);

CREATE TABLE carts
(
    id      bigint auto_increment primary key,
    user_id bigint null,
    constraint UK_unique_user_id
        unique (user_id),
    constraint FK_foreign_key_user_id
        foreign key (user_id) references users (id)
);

CREATE TABLE cart_products
(
    id         bigint auto_increment primary key,
    quantity   int not null,
    cart_id    bigint null,
    product_id bigint null,
    constraint FK_cart_products_cart_reference
        foreign key (cart_id) references carts (id),
    constraint FK_cart_products_product_reference
        foreign key (product_id) references products (id)
);

CREATE TABLE orders
(
    id      bigint auto_increment primary key,
    expiry  varchar(255) null,
    status  varchar(255) null,
    user_id bigint null,
    constraint FK_orders_user_reference
        foreign key (user_id) references users (id)
);

CREATE TABLE order_products
(
    id         bigint auto_increment primary key,
    quantity   int not null,
    order_id   bigint null,
    product_id bigint null,
    constraint FK_order_products_order_reference
        foreign key (order_id) references orders (id),
    constraint FK_order_products_product_reference
        foreign key (product_id) references products (id)
);
