-- Supprimer les formules de toutes les compétences
UPDATE skill
SET formula = NULL;

-- Supprimer la colonne 'formula'
ALTER TABLE skill
    DROP COLUMN formula;

DELETE
FROM flyway_schema_history
WHERE version = '5.2.8';
