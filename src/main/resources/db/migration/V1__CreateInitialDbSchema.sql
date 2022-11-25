CREATE TABLE IF NOT EXISTS person
(
    id       BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login    varchar(50) NOT NULL,
    name     varchar(50) NOT NULL,
    email    varchar(50) NOT NULL,
    birthday timestamp   NOT NULL
);

CREATE TABLE IF NOT EXISTS genre
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS mpa
(
    id   BIGINT      NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS film
(
    id           BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name         varchar(250) NOT NULL,
    description  varchar(250) NOT NULL,
    release_date timestamp    NOT NULL,
    duration     int,
    mpa_id       int,
    rate         int
);

CREATE TABLE IF NOT EXISTS film_genre
(
    film_id  BIGINT NOT NULL REFERENCES film (id),
    genre_id BIGINT NOT NULL REFERENCES genre (id)
);

CREATE TABLE IF NOT EXISTS friendship
(
    subscription_id BIGINT NOT NULL REFERENCES person (id),
    subscriber_id   BIGINT NOT NULL REFERENCES person (id),
    status          varchar,
    CONSTRAINT friendship_pk PRIMARY KEY (subscription_id, subscriber_id),
    CONSTRAINT status_value CHECK (status in ('Accepted', 'Unaccepted'))
);

CREATE TABLE IF NOT EXISTS film_likes
(
    user_id BIGINT NOT NULL REFERENCES person,
    film_id BIGINT NOT NULL REFERENCES film,
    CONSTRAINT film_likes_pk PRIMARY KEY (user_id, film_id)
);