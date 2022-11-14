CREATE DATABASE 'guess-who';

CREATE
OR REPLACE FUNCTION trigger_set_timestamp()
RETURNS TRIGGER AS $$
BEGIN
  NEW.updated_at
= NOW();
RETURN NEW;
END;
$$
LANGUAGE plpgsql;

CREATE TABLE questions
(
    question_id INT PRIMARY KEY,
    external_id UUID UNIQUE         NOT NULL,
    description VARCHAR(255) UNIQUE NOT NULL,
    created_at  TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMPTZ         NOT NULL DEFAULT NOW()
);

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON questions
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();

CREATE TABLE questions_tips
(
    question_tip_id  INT PRIMARY KEY,
    question_id      INT                 NOT NULL,
    tip_title        VARCHAR(255) UNIQUE NOT NULL,
    order_appearance INT                 NOT NULL,
    created_at       TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    updated_at       TIMESTAMPTZ         NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_question FOREIGN KEY (question_id) REFERENCES questions (question_id)
);

CREATE TRIGGER set_timestamp
    BEFORE UPDATE
    ON questions_tips
    FOR EACH ROW
    EXECUTE PROCEDURE trigger_set_timestamp();
