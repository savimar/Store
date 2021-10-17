DROP table IF EXISTS users CASCADE;
DROP table IF EXISTS orders CASCADE;




create table users (  id serial primary key,
                      first_name varchar(200),
                      last_name varchar(200),
                      email varchar(50)
);

create table orders
(
                      id  serial primary key,
                      user_id  integer,
                      price  integer,
                      complete boolean,
                      date timestamp,
                      product varchar(200),
                      operation_id integer,
                      constraint fk_orders_user
                          foreign key (user_id)
                              REFERENCES users (id)

);


