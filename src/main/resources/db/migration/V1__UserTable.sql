CREATE TABLE IF NOT EXISTS "person"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "login" varchar not null,
    "name" varchar not null,
    "email" varchar not null,
    "birthday" timestamp not null
);


