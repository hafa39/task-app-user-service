CREATE TABLE avatars
(
    id     SERIAL PRIMARY KEY,
    user_id      VARCHAR(255),--make unique
    file_name    VARCHAR(255),
    content      BYTEA
);


