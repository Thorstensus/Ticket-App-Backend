CREATE TABLE refresh_tokens
(
    id          bigint auto_increment
        primary key,
    token       varchar(255) null,
    expiry_date date null,
    user_id     bigint null,
    foreign key (user_id) references users (id)
)
