insert into users (id, first_name,last_name, email)
values
(1,'Иван', 'Иванов', 'ivan@1.com'),
(2,'Нина', 'Петрова', 'nina@1.com');

insert into orders (id, user_id, price, complete, date, product, operation_id)
values
(21, 1, 2000, false, CURRENT_DATE, 'Стол', 156748),
(22,1, 50, true, CURRENT_DATE, 'Хлеб', 44786),
(23,2,  10000, false, CURRENT_DATE, 'Табурет', 687879);


