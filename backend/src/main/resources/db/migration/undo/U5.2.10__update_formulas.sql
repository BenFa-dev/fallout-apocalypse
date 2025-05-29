-- Table: derived_stat
UPDATE derived_stat
SET formula = 'AGILITY'
WHERE code = 'ARMOR_CLASS';

DELETE
FROM flyway_schema_history
WHERE version = '5.2.10';