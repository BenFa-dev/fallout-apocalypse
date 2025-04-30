ALTER TABLE character
    DROP COLUMN hit_points;

ALTER TABLE character
    RENAME COLUMN action_points TO current_action_points;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.1';
