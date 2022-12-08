CREATE TABLE user_t
(
    id       bigserial    NOT NULL,
    email    varchar(255) NOT NULL UNIQUE,
    enabled  boolean      NOT NULL,
    password varchar(255) NOT NULL,
    role     varchar(255) NOT NULL,
    username varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);

CREATE TABLE verification_token_t
(
    id          bigserial NOT NULL,
    user_id     bigint    NOT NULL,
    token       varchar(255),
    expire_date timestamp,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user_t (id)
);

CREATE TABLE category_t
(
    id            bigserial NOT NULL,
    cipher        varchar(255),
    category_name varchar(255),
    file_name     varchar(255),
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS verification_token_t
    ADD CONSTRAINT verification_token_fk_user FOREIGN KEY (user_id) REFERENCES user_t ON DELETE CASCADE;