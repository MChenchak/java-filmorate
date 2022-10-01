CREATE TABLE IF NOT EXISTS "film"(
    "id" SERIAL NOT NULL PRIMARY KEY,
    "name" varchar not null,
    "description" varchar not null,
    "release_date" timestamp not null,
    "duration" int
);