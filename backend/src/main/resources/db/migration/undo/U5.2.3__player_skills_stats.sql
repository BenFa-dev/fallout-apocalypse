-- Suppression de la table skill_instance
DROP TABLE IF EXISTS skill_instance;

-- Suppression de la table skill
DROP TABLE IF EXISTS skil;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.3';
