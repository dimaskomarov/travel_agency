DROP TABLE IF EXISTS countries_tours;
DROP TABLE IF EXISTS countries;
DROP TABLE IF EXISTS tours;
DROP TABLE IF EXISTS travel_types;
DROP TABLE IF EXISTS companies;

CREATE TABLE IF NOT EXISTS companies
(
    id      SERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL CHECK (name != ''),
    address VARCHAR(100) NOT NULL CHECK (address != ''),
    age     INT          NOT NULL CHECK (age > 0)
);

CREATE TABLE IF NOT EXISTS travel_types
(
    id   SERIAL PRIMARY KEY,
    type VARCHAR(100) NOT NULL CHECK (type != '')
);

CREATE TABLE IF NOT EXISTS tours
(
    id             SERIAL PRIMARY KEY,
    price          NUMERIC(8, 2) NOT NULL CHECK (price > 0.0),
    amount_day     INT           NOT NULL CHECK (amount_day > 0),
    date_departure TIMESTAMP,
    company_id     INT,
    travel_type_id INT,
    CONSTRAINT company_fk
        FOREIGN KEY (company_id)
            REFERENCES companies (id)
            ON DELETE NO ACTION ,
    CONSTRAINT travel_type_fk
        FOREIGN KEY (travel_type_id)
            REFERENCES travel_types (id)
            ON DELETE NO ACTION
);

CREATE TABLE IF NOT EXISTS countries
(
    id   SERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS countries_tours
(
    country_id INT NOT NULL,
    tour_id    INT NOT NULL,
    CONSTRAINT country_fk
        FOREIGN KEY (country_id)
            REFERENCES countries (id)
            ON DELETE NO ACTION,
    CONSTRAINT tour_fk
        FOREIGN KEY (tour_id)
            REFERENCES tours (id)
            ON DELETE NO ACTION
);

INSERT INTO companies (name, address, age)
VALUES ('Arcadia tour', 'Kharkov, st. Elizavetinskaya 1', 10),
       ('Cactus travel', 'Kharkov, st. Pushka 14', 21);

INSERT INTO travel_types (type)
VALUES ('by plane'),
       ('by train'),
       ('by bus'),
       ('by car'),
       ('by ship');

INSERT INTO tours (price, amount_day, date_departure, company_id, travel_type_id)
VALUES (14670.00, 7, '2020-11-04 17:30:00', 1, 1),
       (18680.00, 7, '2020-11-01 10:00:00', 1, 2),
       (20100.00, 7, '2020-11-08 12:00:00', 1, 3),
       (13660.00, 7, '2020-12-01 09:00:00', 1, 1),
       (7500.00, 7, '2020-11-03 20:00:00', 1, 1),
       (10000.00, 7, '2020-12-02 11:00:00', 1, 5),
       (16660.00, 14, '2020-12-02 10:10:00', 1, 4),
       (34200.00, 7, '2020-12-02 10:10:00', 2, 1);

INSERT INTO countries (name)
VALUES ('Turkey'),
       ('Egypt'),
       ('Tunisia'),
       ('Thailand'),
       ('Spain'),
       ('Austria');

INSERT INTO countries_tours (country_id, tour_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (5, 7),
       (6, 7),
       (2, 8);

SELECT * FROM companies;
UPDATE companies SET address = 'Kharkov, st. Pushkinskaya 15' WHERE name = 'Cactus travel';
DELETE FROM companies WHERE name = 'Cactus travel';

SELECT * FROM travel_types;
UPDATE travel_types SET type = 'by ships' WHERE type = 'by ship';
DELETE FROM travel_types WHERE type = 'by car';

SELECT * FROM tours;
UPDATE tours SET price = 146700.00 WHERE amount_day = 14;
DELETE FROM tours WHERE price = 34200.00;

SELECT * FROM countries;
UPDATE countries SET name = 'Turkey1' WHERE name = 'Turkey';
DELETE FROM countries WHERE name = 'Austria';

SELECT * FROM countries_tours;
UPDATE countries_tours SET country_id = 2 WHERE tour_id = 1;
DELETE FROM countries_tours WHERE country_id = 3;