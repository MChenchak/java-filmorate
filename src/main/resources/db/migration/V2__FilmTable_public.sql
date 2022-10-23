CREATE TABLE IF NOT EXISTS film(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name varchar(250) NOT NULL,
    description varchar(250) NOT NULL,
    release_date timestamp NOT NULL,
    duration int
);
