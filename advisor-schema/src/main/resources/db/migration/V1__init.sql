--CREATE SCHEMA IF NOT EXISTS advisory_schema;
--USE SCHEMA 'advisory_schema';

CREATE TYPE user_type AS ENUM (
    'applicant',
    'advisor'
    );

CREATE TYPE advisors_role AS ENUM (
    'associate',
    'partner',
    'senior'
    );

CREATE TYPE phone_number_type AS ENUM (
    'home',
    'work',
    'mobile'
    );

CREATE TYPE application_status AS ENUM (
    'new',
    'assigned',
    'on_hold',
    'approved',
    'canceled',
    'declined'
    );

CREATE TABLE IF NOT EXISTS users
(
    id       BIGSERIAL CONSTRAINT users_PK PRIMARY KEY,
    username VARCHAR(255) NOT NULL CONSTRAINT users_username_UK UNIQUE,
    email    VARCHAR(255) NOT NULL CONSTRAINT users_email_UK UNIQUE,
    "type"   user_type    NOT NULL,
    version                    BIGINT                   NOT NULL DEFAULT 0,
    CONSTRAINT email_format_check CHECK (email ~* '^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\.[A-Za-z]{2,}$')
);

CREATE TABLE IF NOT EXISTS advisors
(
    id     BIGINT CONSTRAINT advisors_PK PRIMARY KEY,
    "role" advisors_role NOT NULL,
    version                    BIGINT                   NOT NULL DEFAULT 0,
    CONSTRAINT advisors_users_FK FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS applicants
(
    id     BIGINT CONSTRAINT applicants_PK PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    ssn        VARCHAR(255) NOT NULL,
    version                    BIGINT                   NOT NULL DEFAULT 0,
    CONSTRAINT applicants_users_FK FOREIGN KEY (id) REFERENCES users (id) ON DELETE CASCADE
);


CREATE TABLE IF NOT EXISTS addresses
(
    applicant_id BIGINT CONSTRAINT addresses_PK PRIMARY KEY,
    city   VARCHAR(255) CONSTRAINT validate_city CHECK (city ~ '^[A-Za-z]+$') NOT NULL ,
    street VARCHAR(255) NOT NULL,
    "number" VARCHAR(5) CONSTRAINT validate_number CHECK ("number" ~ '^[1-9][0-9]{0,3}[A-Za-z]$') NOT NULL,
    zip    VARCHAR(5) CONSTRAINT validate_zip CHECK (zip ~ '^\d{5}$') NOT NULL ,
    apt    VARCHAR(5) CONSTRAINT validate_apt CHECK (apt ~ '^[1-9][0-9]{0,3}[A-Za-z]$') NULL,
    version                    BIGINT                   NOT NULL DEFAULT 0,
    CONSTRAINT applications_applicants_FK FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS phone_numbers
(
    applicant_id BIGINT NOT NULL,
    "number" VARCHAR(15)       CONSTRAINT phone_numbers_number_UK UNIQUE NOT NULL,
    "type"   phone_number_type NOT NULL,
    version                    BIGINT                   NOT NULL DEFAULT 0,
    CONSTRAINT phone_numbers_PK PRIMARY KEY (applicant_id, "number"),
    CONSTRAINT phone_numbers_applicants_FK FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON DELETE CASCADE,
    CONSTRAINT phone_number_validation CHECK ("number" ~ '^(\+[1-9]{2,3}[0-9]{9}|0[0-9]{9}|101|102|103|112|911)$')
);

CREATE TABLE IF NOT EXISTS applications
(
    id           BIGSERIAL  CONSTRAINT applications_PK PRIMARY KEY,
    money        numeric(21, 4)           NOT NULL,
    "status"     application_status       NOT NULL DEFAULT 'new',
    created_at   TIMESTAMP WITH TIME ZONE NOT NULL default now(),
    assigned_at  TIMESTAMP WITH TIME ZONE NULL,
    applicant_id BIGINT                   NOT NULL,
    advisor_id   BIGINT                   NULL,
    version                    BIGINT                   NOT NULL DEFAULT 0,
    CONSTRAINT applications_applicants_FK FOREIGN KEY (applicant_id) REFERENCES applicants (id) ON DELETE RESTRICT,
    CONSTRAINT applications_advisors_FK FOREIGN KEY (advisor_id) REFERENCES advisors (id) ON DELETE RESTRICT,
    CONSTRAINT validate_assigned_at CHECK ((assigned_at IS NULL AND advisor_id IS NULL AND "status" = 'new') OR (advisor_id IS NOT NULL AND "status" != 'new'))
);
