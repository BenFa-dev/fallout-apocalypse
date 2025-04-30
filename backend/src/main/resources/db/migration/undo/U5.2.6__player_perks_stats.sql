DROP TABLE IF EXISTS perk_instance;

DROP TABLE IF EXISTS perk;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.6';
