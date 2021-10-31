insert into users (id, first_name,last_name, email)
values
(1,'Иван', 'Иванов', 'ivan@1.com'),
(2,'Нина', 'Петрова', 'nina@1.com');

insert into orders (id, user_id, price, complete, date, product, operation_id, order_status)
values
(21, 1, 2000, false, CURRENT_DATE, 'Стол', 156748, 'paid'),
(22,1, 50, true, CURRENT_DATE, 'Хлеб', 44786, 'shipped'),
(23,2, 100, true, CURRENT_DATE, 'Капуста', 44786, 'shipped'),
(24,2,  10000, false, CURRENT_DATE, 'Шкаф', 687879, 'rejected');


