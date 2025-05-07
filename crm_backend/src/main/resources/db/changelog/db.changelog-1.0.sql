CREATE TABLE candidate_status(
    id SERIAL PRIMARY KEY,
    status VARCHAR(128) NOT NULL UNIQUE,
    color VARCHAR(20) NOT NULL

);
CREATE TABLE duty_type(
    id SERIAL PRIMARY KEY,
    status VARCHAR(128) NOT NULL UNIQUE,
    color VARCHAR(20) NOT NULL
);
CREATE TABLE psychological_test(
    id SERIAL PRIMARY KEY,
    status VARCHAR(128) NOT NULL UNIQUE,
    color VARCHAR(20) NOT NULL
);
CREATE TABLE military_unit (
    id SERIAL PRIMARY KEY,
    unit_name VARCHAR(500) NULL NULL UNIQUE,
    strict_unit_name VARCHAR(500) NULL NULL UNIQUE,
    comment VARCHAR(128)
);
CREATE TABLE candidates (
    id SERIAL PRIMARY KEY,
    surname VARCHAR(128) NOT NULL,
    name VARCHAR(128) NOT NULL,
    middle_name VARCHAR(128) NOT NULL,
    status INT NOT NULL REFERENCES candidate_status(id) ON DELETE RESTRICT,
    phone_number VARCHAR(64) NOT NULL,
    duty_type INT NOT NULL REFERENCES duty_type(id) ON DELETE RESTRICT,
    military_unit INT NOT NULL REFERENCES military_unit(id) ON DELETE RESTRICT,
    recommendation_letter DATE NOT NULL,
    recruiter VARCHAR(128) NOT NULL,
    curator VARCHAR(128) NOT NULL,
    escorted_by VARCHAR(128) NOT NULL,
    psychological_test INT NOT NULL REFERENCES psychological_test(id) ON DELETE RESTRICT,
    enrolment_order DATE,
    order_number VARCHAR(64),
    territory_center_record VARCHAR(254)
);

CREATE TABLE candidate_comments(
    id SERIAL PRIMARY KEY,
    comment_date DATE,
    commented_by VARCHAR(128),
    comment TEXT,
    candidate_id INT REFERENCES candidates(id)
);

CREATE TABLE candidate_documents(
    id SERIAL PRIMARY KEY,
    file_name VARCHAR(255) NOT NULL,
    file_type VARCHAR(100) NOT NULL,
    file_path VARCHAR(500) NOT NULL,
    uploaded_date DATE NOT NULL,
    uploaded_by VARCHAR(128) NOT NULL,
    type VARCHAR(512) NOT NULL,
    candidate_id INT REFERENCES candidates(id)
);