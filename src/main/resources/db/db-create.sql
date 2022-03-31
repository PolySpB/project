-- select * from dict.currency;
-- select * from main.bankbook;
-- select * from main.user;

create schema dict;
create schema main;

create sequence dict.currency_id_seq;
create table dict.currency (
    id integer unique not null default nextval('dict.currency_id_seq'),
    currency varchar not null,
    primary key (id)
);

insert into dict.currency (currency) values ('USD'), ('RUB'), ('EUR'), ('GBP');

create sequence main.bankbook_id_seq;
create table main.bankbook (
    id integer unique not null default nextval('main.bankbook_id_seq'),
    user_id integer not null,
    number varchar not null,
    amount decimal not null,
    currency_id integer not null,
    primary key (id),
    foreign key (currency_id) references dict.currency(id),
    foreign key (user_id) references main.user(id)
);

create sequence main.user_id_seq;
create table main.user (
    id integer unique not null default nextval('main.user_id_seq'),
    user_name varchar not null,
    email varchar not null,
    primary key (id)
);

insert into main.user (user_name, email) values ('Anna', 'anna@mail.com');