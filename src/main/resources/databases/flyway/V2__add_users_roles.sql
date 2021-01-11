--DROP TABLE IF EXISTS users;
--DROP TABLE IF EXISTS roles;
--DROP TABLE IF EXISTS users_roles;

CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL PRIMARY KEY,
    login       VARCHAR(100) NOT NULL CHECK (login != ''),
    password    VARCHAR(100) NOT NULL CHECK (password != '')
);

CREATE TABLE IF NOT EXISTS roles
(
    id      BIGSERIAL PRIMARY KEY,
    name    VARCHAR(100) NOT NULL CHECK (name != '')
);

CREATE TABLE IF NOT EXISTS users_roles
(
    user_id BIGINT,
    role_id BIGINT,
    CONSTRAINT user_id_fk
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE SET NULL ,
    CONSTRAINT role_id_fk
        FOREIGN KEY (role_id)
            REFERENCES roles (id)
            ON DELETE SET NULL
);