CREATE TABLE work_unit(
    id BIGSERIAL PRIMARY KEY,
    unit_name VARCHAR(512) NOT NULL,
    is_disabled BOOLEAN DEFAULT FALSE
);
CREATE TABLE user_position(
    id BIGSERIAL PRIMARY KEY,
    unit_position VARCHAR(512) NOT NULL,
    is_disabled BOOLEAN DEFAULT FALSE
);

CREATE TABLE app_user(
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(128) NOT NULL,
    second_name VARCHAR(128) NOT NULL,
    work_unit_id BIGINT NOT NULL REFERENCES work_unit(id) ON DELETE RESTRICT,
    user_position_id BIGINT NOT NULL REFERENCES user_position(id) ON DELETE RESTRICT,
    username VARCHAR(128) NOT NULL,
    password VARCHAR(512) NOT NULL,
    role VARCHAR(16) NOT NULL,
    is_account_non_expired BOOLEAN,
    is_account_non_locked BOOLEAN,
    is_credentials_non_expired BOOLEAN
);