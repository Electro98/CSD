DROP TABLE IF EXISTS stationery;
CREATE TABLE stationery (
    id integer NOT NULL PRIMARY KEY,
    name varchar NOT NULL,
    type varchar NOT NULL,
    price real NOT NULL,
    num_in_box int NOT NULL);

insert into stationery (name, type, price, num_in_box) values
    ('Berlingo xGold 0.7 Blue', 'pen', 55.5, 120),
    ('Berlingo xGold 0.7 Red', 'pen', 56.5, 120),
    ('Berlingo Instinct', 'eraser', 40.1, 50),
    ('Cat - pen cap', 'pen cap', 10.1, 20),
    ('Good old eraser', 'eraser', 5.42, 42);
