CREATE TABLE work_unit(
    id SERIAL PRIMARY KEY,
    unit_name VARCHAR(512) NOT NULL
);
CREATE TABLE user_position(
    id SERIAL PRIMARY KEY,
    unit_position VARCHAR(512) NOT NULL
);

CREATE TABLE app_user(
    id SERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    second_name VARCHAR(128) NOT NULL,
    work_unit INT NOT NULL REFERENCES work_unit(id) ON DELETE RESTRICT,
    user_position INT NOT NULL REFERENCES user_position(id) ON DELETE RESTRICT,
    username VARCHAR(128) NOT NULL,
    password VARCHAR(512) NOT NULL,
    role VARCHAR(16) NOT NULL,
    is_account_non_expired BOOLEAN,
    is_account_non_locked BOOLEAN,
    is_credentials_non_expired BOOLEAN
);