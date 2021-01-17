INSERT INTO companies (name, address, age)
VALUES ('Arcadia tour', 'Kharkov, st. Elizavetinskaya 1', 10),
       ('Cactus travel', 'Kharkov, st. Pushka 14', 21);

INSERT INTO travel_types (name)
VALUES ('by plane'),
       ('by train'),
       ('by bus'),
       ('by car'),
       ('by ship');

INSERT INTO tours ( price, amount_days, date_departure, company_id, travel_type_id)
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


INSERT INTO roles(role)
VALUES ('ADMIN'),
       ('MANAGER'),
       ('DEFAULT');

INSERT INTO users(login, password)
VALUES ('admin', '$2y$12$4WxWj9TVLRsgEo/j9L1sUezd.uf38YCCCCdIAKB0gxm/5KF0VJet.'),
       ('manager', '$2y$12$UXKFHkPSMGre6Z9Rs41z8OHyC8OuEQPFA.b8rl5vjYw4oOR7VkqJO'),
       ('customer', '$2y$12$yp4cGHgL0WA6Jv66FKAkjOJkT5GdwepP5kw/63.LsJQhXcJqI.SPi');

INSERT INTO users_roles(user_id, role_id)
VALUES (1, 1),
       (1, 2),
       (1, 3),
       (2, 2),
       (2, 3),
       (3, 3);