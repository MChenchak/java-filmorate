CREATE TABLE IF NOT EXISTS person(
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    login varchar(50) NOT NULL,
    name varchar(50) NOT NULL,
    email varchar(50) NOT NULL,
    birthday timestamp NOT NULL
);

