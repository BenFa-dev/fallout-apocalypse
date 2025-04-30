-- ================================================
-- 0. Renommer la table damage_type pour libérer le nom ENUM
-- ================================================
ALTER TABLE damage_type
    RENAME TO damage_type_table;

-- ================================================
-- 1. Recréation de l'ancien ENUM damage_type
-- ================================================
CREATE TYPE damage_type AS ENUM (
    'NORMAL',
    'ELECTRICAL',
    'PLASMA',
    'LASER',
    'FIRE',
    'EMP',
    'EXPLOSIVE'
    );

-- ================================================
-- 2. Recréation de l'ancienne colonne weapon.damage_type
-- ================================================
ALTER TABLE weapon
    ADD COLUMN damage_type damage_type;

-- Restaurer les anciennes valeurs de weapon à partir de damage_type_table
UPDATE weapon
SET damage_type = (SELECT dt.code::damage_type
                   FROM damage_type_table dt
                   WHERE dt.id = weapon.damage_type_id);

-- Supprimer la colonne FK
ALTER TABLE weapon
    DROP COLUMN damage_type_id;

-- ================================================
-- 3. Recréation des anciennes colonnes armor
-- ================================================
ALTER TABLE armor
    ADD COLUMN damage_threshold_normal     INTEGER,
    ADD COLUMN damage_threshold_laser      INTEGER,
    ADD COLUMN damage_threshold_fire       INTEGER,
    ADD COLUMN damage_threshold_plasma     INTEGER,
    ADD COLUMN damage_threshold_explosive  INTEGER,
    ADD COLUMN damage_threshold_electric   INTEGER,
    ADD COLUMN damage_resistance_normal    INTEGER,
    ADD COLUMN damage_resistance_laser     INTEGER,
    ADD COLUMN damage_resistance_fire      INTEGER,
    ADD COLUMN damage_resistance_plasma    INTEGER,
    ADD COLUMN damage_resistance_explosive INTEGER,
    ADD COLUMN damage_resistance_electric  INTEGER;

-- Remplir armor depuis armor_damage + damage_type_table
UPDATE armor a
SET damage_threshold_normal     = (SELECT ad.threshold
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'NORMAL'),
    damage_resistance_normal    = (SELECT ad.resistance
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'NORMAL'),
    damage_threshold_laser      = (SELECT ad.threshold
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'LASER'),
    damage_resistance_laser     = (SELECT ad.resistance
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'LASER'),
    damage_threshold_fire       = (SELECT ad.threshold
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'FIRE'),
    damage_resistance_fire      = (SELECT ad.resistance
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'FIRE'),
    damage_threshold_plasma     = (SELECT ad.threshold
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'PLASMA'),
    damage_resistance_plasma    = (SELECT ad.resistance
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'PLASMA'),
    damage_threshold_explosive  = (SELECT ad.threshold
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'EXPLOSIVE'),
    damage_resistance_explosive = (SELECT ad.resistance
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'EXPLOSIVE'),
    damage_threshold_electric   = (SELECT ad.threshold
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'ELECTRICAL'),
    damage_resistance_electric  = (SELECT ad.resistance
                                   FROM armor_damage ad
                                            JOIN damage_type_table dt ON ad.damage_type_id = dt.id
                                   WHERE ad.armor_id = a.id
                                     AND dt.code = 'ELECTRICAL');

-- ================================================
-- 4. Nettoyage final
-- ================================================
DROP TABLE IF EXISTS armor_damage;
DROP TABLE IF EXISTS damage_type_table;

-- ================================================
-- 5. Suppression Flyway
-- ================================================
DELETE
FROM flyway_schema_history
WHERE version = '5.2.0';
