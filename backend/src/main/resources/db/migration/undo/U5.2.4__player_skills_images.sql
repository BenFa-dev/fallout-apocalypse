ALTER TABLE skill
    DROP COLUMN image_path;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.4';