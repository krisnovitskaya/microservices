set search_path to ms;
create table products (
    id                      bigserial primary key,
    title                   varchar(255),
    price                   int
);

create table orders (
    id                      bigserial primary key,
    price                   int,
	items					bigint ARRAY
);

insert into orders 	(price, items)
values
('30', '{ 1, 2, 5}'),
('300', '{ 1, 10, 7}'),
('10', '{ 2, 2, 5}');


insert into products (title, price)
values
('Potato', 30),
('Tomato', 160),
('Carrot', 40),
('Cabbage', 35),
('Pepper', 185),
('Chili pepper', 190),
('Eggplant', 200),
('Cauliflower', 170),
('Onion', 25),
('Garlic', 150);
