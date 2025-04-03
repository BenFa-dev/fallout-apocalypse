-- Undo V5.1.0__inventory_create_tables.sql

-- Suppression de la contrainte de clé étrangère explicite
ALTER TABLE item_instance
    DROP CONSTRAINT IF EXISTS fk_item_instance_inventory;

-- Suppression des tables (ordre inverse de création)
DROP TABLE IF EXISTS inventory CASCADE;
DROP TABLE IF EXISTS weapon_instance_ammo CASCADE;
DROP TABLE IF EXISTS armor_instance CASCADE;
DROP TABLE IF EXISTS weapon_instance CASCADE;
DROP TABLE IF EXISTS item_instance CASCADE;
DROP TABLE IF EXISTS weapon_mode CASCADE;
DROP TABLE IF EXISTS weapon_compatible_ammo CASCADE;
DROP TABLE IF EXISTS ammo CASCADE;
DROP TABLE IF EXISTS armor CASCADE;
DROP TABLE IF EXISTS weapon CASCADE;
DROP TABLE IF EXISTS item CASCADE;

-- Suppression des types ENUM utilisés
DROP TYPE IF EXISTS damage_type CASCADE;
DROP TYPE IF EXISTS weapon_mode_type CASCADE;
DROP TYPE IF EXISTS weapon_type CASCADE;
DROP TYPE IF EXISTS item_type CASCADE;

DELETE
FROM flyway_schema_history
WHERE version IN (
                  '5.1.0',
                  '5.1.1',
                  '5.1.2.1',
                  '5.1.2.2',
                  '5.1.2.3',
                  '5.1.2.4',
                  '5.1.2.5',
                  '5.1.2.6',
                  '5.1.3',
                  '5.1.4'
    );
