CREATE TABLE IF NOT EXISTS film_likes(
    user_id BIGINT NOT NULL REFERENCES person,
    film_id BIGINT NOT NULL REFERENCES film,
    primary key (user_id, film_id)

);