--DROP TABLE IF EXISTS countries_tours;
--DROP TABLE IF EXISTS countries;
--DROP TABLE IF EXISTS tours;
--DROP TABLE IF EXISTS travel_types;
--DROP TABLE IF EXISTS companies;

CREATE TABLE IF NOT EXISTS companies
(
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(100) NOT NULL CHECK (name != ''),
    address     VARCHAR(100) ,
    age         INT CHECK (age > 0)
);

CREATE TABLE IF NOT EXISTS travel_types
(
    id   BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS tours
(
    id              BIGSERIAL PRIMARY KEY,
    price           NUMERIC(8, 2) NOT NULL CHECK (price > 0.0),
    amount_days     INT           NOT NULL CHECK (amount_days > 0),
    date_departure  TIMESTAMP,
    company_id      BIGINT,
    travel_type_id  BIGINT,
    CONSTRAINT company_fk
        FOREIGN KEY (company_id)
            REFERENCES companies (id)
            ON DELETE SET NULL ,
    CONSTRAINT travel_type_fk
        FOREIGN KEY (travel_type_id)
            REFERENCES travel_types (id)
            ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS countries
(
    id      BIGSERIAL PRIMARY KEY,
    name VARCHAR(70) NOT NULL CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS countries_tours
(
    country_id  BIGINT,
    tour_id     BIGINT,
    CONSTRAINT country_fk
        FOREIGN KEY (country_id)
            REFERENCES countries (id)
            ON DELETE SET NULL,
    CONSTRAINT tour_fk
        FOREIGN KEY (tour_id)
            REFERENCES tours (id)
            ON DELETE SET NULL
);