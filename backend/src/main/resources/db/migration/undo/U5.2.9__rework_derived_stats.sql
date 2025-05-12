DROP TABLE IF EXISTS derived_stat;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.9';
