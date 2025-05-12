CREATE SEQUENCE candidate_status_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE duty_type_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE psychological_test_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE military_unit_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE work_unit_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE user_position_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE app_user_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE candidate_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE candidate_comments_id_seq START 1 INCREMENT BY 1;
CREATE SEQUENCE candidate_documents_id_seq START 1 INCREMENT BY 1;

CREATE TABLE candidate_status(
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(128) NOT NULL UNIQUE,
    color VARCHAR(20) NOT NULL

);
CREATE TABLE duty_type(
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(128) NOT NULL UNIQUE,
    color VARCHAR(20) NOT NULL
);
CREATE TABLE psychological_test(
    id BIGSERIAL PRIMARY KEY,
    status VARCHAR(128) NOT NULL UNIQUE,
    color VARCHAR(20) NOT NULL
);
CREATE TABLE military_unit (
    id BIGSERIAL PRIMARY KEY,
    unit_name VARCHAR(500) NULL NULL UNIQUE,
    strict_unit_name VARCHAR(500) NULL NULL UNIQUE,
    comment VARCHAR(128)
);
CREATE TABLE candidates (
    id BIGSERIAL PRIMARY KEY,
    surname VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128) NOT NULL,
    candidate_status_id BIGINT NOT NULL REFERENCES candidate_status(id) ON DELETE RESTRICT,
    phone_number VARCHAR(64) NOT NULL,
    duty_type_id BIGINT NOT NULL REFERENCES duty_type(id) ON DELETE RESTRICT,
    military_unit_id BIGINT NOT NULL REFERENCES military_unit(id) ON DELETE RESTRICT,
    recommendation_letter DATE NOT NULL,
    recruiter VARCHAR(128),
    curator VARCHAR(128),
    escorted_by VARCHAR(128),
    psychological_test BIGINT NOT NULL REFERENCES psychological_test(id) ON DELETE RESTRICT,
    enrolment_order DATE,
    order_number VARCHAR(64),
    territory_center_record VARCHAR(254)
);

CREATE INDEX idx_phone_number ON candidates(phone_number);
CREATE INDEX idx_surname ON candidates(surname);
CREATE INDEX idx_middle_name ON candidates(middle_name);
CREATE INDEX idx_recruiter ON candidates(recruiter);
CREATE INDEX idx_curator ON candidates(curator);
CREATE INDEX idx_escorted_by ON candidates(escorted_by);

CREATE TABLE candidate_comments(
    id BIGSERIAL PRIMARY KEY,
    comment_date DATE,
    commented_by VARCHAR(128),
    comment TEXT,
    candidate_id BIGINT REFERENCES candidates(id)
);

CREATE TABLE candidate_documents(
    id BIGSERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    uploaded_date DATE NOT NULL,
    uploaded_by VARCHAR(128) NOT NULL,
    type VARCHAR(512) NOT NULL,
    candidate_id BIGINT REFERENCES candidates(id)
);