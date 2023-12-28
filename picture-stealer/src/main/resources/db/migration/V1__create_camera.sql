CREATE TABLE IF NOT EXISTS camera
(
    id         VARCHAR(36)              NOT NULL,
    nasa_id    VARCHAR(255) UNIQUE      NOT NULL,
    "name"     VARCHAR(255)             NULL,
    full_name  VARCHAR(255)             NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT camera_pkey PRIMARY KEY (id)
);