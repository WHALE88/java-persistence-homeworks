CREATE TABLE IF NOT EXISTS user_user
(
    id BIGSERIAL CONSTRAINT PK_user_user PRIMARY KEY,
    email VARCHAR(255)                       NULL,
    first_name VARCHAR(255)                  NOT NULL,
    last_name  VARCHAR(255)                  NULL
);

INSERT INTO user_user (email, first_name, last_name) VALUES ('test@gmail.com', 'Maksym', 'Oliinyk');