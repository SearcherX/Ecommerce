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

CREATE TABLE subcategory_t
(
    id               bigserial NOT NULL,
    cipher           varchar(255),
    file_name        varchar(255),
    subcategory_name varchar(255),
    category_id      bigint,
    PRIMARY KEY (id)
);

CREATE TABLE product_t
(
    id             bigserial NOT NULL,
    cipher         varchar(255),
    description    varchar(255),
    present        boolean,
    price          float(53),
    product_name   varchar(255),
    subcategory_id bigint,
    PRIMARY KEY (id)
);

ALTER TABLE IF EXISTS verification_token_t
    ADD CONSTRAINT verification_token_fk_user FOREIGN KEY (user_id) REFERENCES user_t ON DELETE CASCADE;

ALTER TABLE IF EXISTS subcategory_t
    ADD CONSTRAINT subcategory_fk_category FOREIGN KEY (category_id) REFERENCES category_t ON DELETE CASCADE;

ALTER TABLE IF EXISTS product_t
    ADD CONSTRAINT product_fk_subcategory FOREIGN KEY (subcategory_id) REFERENCES subcategory_t ON DELETE CASCADE;