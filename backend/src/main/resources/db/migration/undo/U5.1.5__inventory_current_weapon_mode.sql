ALTER TABLE weapon_instance
    DROP COLUMN IF EXISTS current_weapon_mode_id;

-- Suppression de l'entrée dans flyway_schema_history
DELETE
FROM flyway_schema_history
WHERE version = '5.1.5';