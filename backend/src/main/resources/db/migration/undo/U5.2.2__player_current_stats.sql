ALTER TABLE character
    DROP COLUMN level,
    DROP COLUMN is_poisoned,
    DROP COLUMN is_radiated,
    DROP COLUMN has_eye_damage,
    DROP COLUMN is_right_arm_crippled,
    DROP COLUMN is_left_arm_crippled,
    DROP COLUMN is_right_leg_crippled,
    DROP COLUMN is_left_leg_crippled;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.2';
