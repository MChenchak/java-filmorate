# java-filmorate
![Сема базы данных](https://github.com/MChenchak/java-filmorate/blob/main/database%20schema.png)

Для **жанров**, **категорий** фильмов и **статусов** дружбы использованы Enum`ы вместо создания дополнительных таблиц. 

## Скрипты создания таблиц
```
CREATE TYPE "friendship_status" AS ENUM (
  'Accepted',
  'Unaccepted'
);

CREATE TYPE "film_genre" AS ENUM (
  'Comedy',
  'Drama',
  'Cartoon',
  'Thriller',
  'Documentary',
  'Action'
);

CREATE TYPE "film_rating" AS ENUM (
  'G',
  'PG',
  'PG_13',
  'NC_17'
);

CREATE TABLE "users" (
  "id" long PRIMARY KEY,
  "login" varchar,
  "name" varchar,
  "email" varchar,
  "birthday" timestamp
);

CREATE TABLE "film" (
  "id" long PRIMARY KEY,
  "name" varchar,
  "cdescription" varchar,
  "release_date" timestamp,
  "genre" film_genre,
  "rating" film_rating,
  "duration" int
);

CREATE TABLE "friendship" (
  "subscription_id" long,
  "subscriber_id" long,
  "status" friendship_status,
  PRIMARY KEY ("subscription_id", "subscriber_id")
);

CREATE TABLE "user_films" (
  "user_id" long,
  "film_id" long,
  PRIMARY KEY ("user_id", "film_id")
);

ALTER TABLE "friendship" ADD FOREIGN KEY ("subscription_id") REFERENCES "users" ("id");

ALTER TABLE "friendship" ADD FOREIGN KEY ("subscriber_id") REFERENCES "users" ("id");

ALTER TABLE "user_films" ADD FOREIGN KEY ("user_id") REFERENCES "users" ("id");

ALTER TABLE "user_films" ADD FOREIGN KEY ("film_id") REFERENCES "film" ("id");
```
