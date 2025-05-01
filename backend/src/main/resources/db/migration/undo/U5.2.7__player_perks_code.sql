UPDATE perk
SET code = LOWER(code);

ALTER TABLE damage_type
    DROP COLUMN created_at,
    DROP COLUMN updated_at,
    DROP COLUMN image_path;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.7';
