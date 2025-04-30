ALTER TABLE character
    ADD COLUMN level                 INTEGER NOT NULL DEFAULT 0,
    ADD COLUMN is_poisoned           BOOL    NOT NULL DEFAULT false,
    ADD COLUMN is_radiated           BOOL    NOT NULL DEFAULT false,
    ADD COLUMN has_eye_damage        BOOL    NOT NULL DEFAULT false,
    ADD COLUMN is_right_arm_crippled BOOL    NOT NULL DEFAULT false,
    ADD COLUMN is_left_arm_crippled  BOOL    NOT NULL DEFAULT false,
    ADD COLUMN is_right_leg_crippled BOOL    NOT NULL DEFAULT false,
    ADD COLUMN is_left_leg_crippled  BOOL    NOT NULL DEFAULT false;