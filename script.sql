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

CREATE TABLE users (
    id integer NOT NULL PRIMARY KEY,
    username varchar NOT NULL UNIQUE,
    password varchar NOT NULL,
    role varchar NOT NULL);

-- password for 'admin' is 'admin'
-- password for 'user' is 'user'
insert into users (username, password, role) values
    ('admin', '$2a$10$/1oM2ocQOwNur2rbRGWyE.yydJ1DVvIjMMwSG27TBAWAIlX19QyU6', 'ROLE_ADMIN'),
    ('user', '$2a$10$MBsVQIeKwlZIpaY6cJsHN.OE899XSAGhGSS3lMlqxEM12cZJOwNzy', 'ROLE_USER');
