ALTER TABLE character
    RENAME COLUMN current_action_points TO action_points;

ALTER TABLE character
    ADD COLUMN hit_points INTEGER NOT NULL DEFAULT 0;