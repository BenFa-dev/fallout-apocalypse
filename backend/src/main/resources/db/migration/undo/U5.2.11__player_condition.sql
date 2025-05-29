-- Table: condition
DROP TABLE IF EXISTS condition_instance CASCADE;
DROP TABLE IF EXISTS condition CASCADE;

ALTER TABLE character
    ADD COLUMN is_poisoned           BOOL NOT NULL DEFAULT false,
    ADD COLUMN is_radiated           BOOL NOT NULL DEFAULT false,
    ADD COLUMN has_eye_damage        BOOL NOT NULL DEFAULT false,
    ADD COLUMN is_right_arm_crippled BOOL NOT NULL DEFAULT false,
    ADD COLUMN is_left_arm_crippled  BOOL NOT NULL DEFAULT false,
    ADD COLUMN is_right_leg_crippled BOOL NOT NULL DEFAULT false,
    ADD COLUMN is_left_leg_crippled  BOOL NOT NULL DEFAULT false;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.11';