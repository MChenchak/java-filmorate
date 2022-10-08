CREATE TABLE IF NOT EXISTS "test.person"(
    "id" INTEGER NOT NULL AUTO_INCREMENT PRIMARY KEY,
    "login" varchar(50) NOT NULL,
    "name" varchar(50) NOT NULL,
    "email" varchar(50) NOT NULL,
    "birthday" timestamp NOT NULL
);


