CREATE TABLE IF NOT EXISTS photo
(
    id         VARCHAR(36)              NOT NULL,
    nasa_id    VARCHAR(255) UNIQUE      NOT NULL,
    "name"     VARCHAR(255)             NULL,
    camera_id  VARCHAR(36)              NULL,
    img_src    VARCHAR(255)             NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,

    CONSTRAINT photo_pkey PRIMARY KEY (id),
    CONSTRAINT photo_camera_id_fkey FOREIGN KEY (camera_id) REFERENCES camera (id)
);
