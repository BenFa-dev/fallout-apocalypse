ALTER TYPE damage_type RENAME TO damage_type_old;

-- ================================================
-- 1. Création de la table damage_type
-- ================================================
CREATE TABLE damage_type
(
    id            SERIAL PRIMARY KEY,
    code          TEXT    NOT NULL UNIQUE,
    names         JSONB   NOT NULL,
    descriptions  JSONB   NOT NULL,
    display_order INTEGER NOT NULL DEFAULT 0,
    visible       BOOLEAN NOT NULL DEFAULT TRUE
);

-- ================================================
-- 2. Remplissage des damage_type standards
-- ================================================
INSERT INTO damage_type (code, names, descriptions, display_order, visible)
VALUES ('NORMAL', '{
  "en": "Normal",
  "fr": "Normal"
}', '{
  "en": "Physical damage.",
  "fr": "Dégâts physiques."
}', 0, true),
       ('ELECTRICAL', '{
         "en": "Electrical",
         "fr": "Électrique"
       }', '{
         "en": "Electrical shock damage.",
         "fr": "Dégâts d\"électricité."
       }', 1, true),
       ('PLASMA', '{
         "en": "Plasma",
         "fr": "Plasma"
       }', '{
         "en": "Plasma energy damage.",
         "fr": "Dégâts d\"énergie plasma."
       }', 2, true),
       ('LASER', '{
         "en": "Laser",
         "fr": "Laser"
       }', '{
         "en": "Laser beam damage.",
         "fr": "Dégâts de faisceau laser."
       }', 3, true),
       ('FIRE', '{
         "en": "Fire",
         "fr": "Feu"
       }', '{
         "en": "Burn damage.",
         "fr": "Dégâts de brûlure."
       }', 4, true),
       ('EMP', '{
         "en": "EMP",
         "fr": "IEM"
       }', '{
         "en": "Electromagnetic Pulse damage.",
         "fr": "Dégâts d\"impulsion électromagnétique."
       }', 5, true),
       ('EXPLOSIVE', '{
         "en": "Explosive",
         "fr": "Explosif"
       }', '{
         "en": "Explosion damage.",
         "fr": "Dégâts d\"explosion."
       }', 6, true);

-- ================================================
-- 3. Migration de armor vers armor_damage
-- ================================================
CREATE TABLE armor_damage
(
    id             SERIAL PRIMARY KEY,
    armor_id       BIGINT  NOT NULL REFERENCES armor (id) ON DELETE CASCADE,
    damage_type_id BIGINT  NOT NULL REFERENCES damage_type (id),
    threshold      INTEGER NOT NULL,
    resistance     INTEGER NOT NULL,
    UNIQUE (armor_id, damage_type_id)
);

-- Migration des anciennes colonnes armor -> armor_damage
INSERT INTO armor_damage (armor_id, damage_type_id, threshold, resistance)
SELECT id, (SELECT id FROM damage_type WHERE code = 'NORMAL'), damage_threshold_normal, damage_resistance_normal
FROM armor
UNION ALL
SELECT id, (SELECT id FROM damage_type WHERE code = 'LASER'), damage_threshold_laser, damage_resistance_laser
FROM armor
UNION ALL
SELECT id, (SELECT id FROM damage_type WHERE code = 'FIRE'), damage_threshold_fire, damage_resistance_fire
FROM armor
UNION ALL
SELECT id, (SELECT id FROM damage_type WHERE code = 'PLASMA'), damage_threshold_plasma, damage_resistance_plasma
FROM armor
UNION ALL
SELECT id,
       (SELECT id FROM damage_type WHERE code = 'EXPLOSIVE'),
       damage_threshold_explosive,
       damage_resistance_explosive
FROM armor
UNION ALL
SELECT id, (SELECT id FROM damage_type WHERE code = 'ELECTRICAL'), damage_threshold_electric, damage_resistance_electric
FROM armor;

-- Suppression des anciennes colonnes inutiles dans armor
ALTER TABLE armor
    DROP COLUMN damage_threshold_normal,
    DROP COLUMN damage_threshold_laser,
    DROP COLUMN damage_threshold_fire,
    DROP COLUMN damage_threshold_plasma,
    DROP COLUMN damage_threshold_explosive,
    DROP COLUMN damage_threshold_electric,
    DROP COLUMN damage_resistance_normal,
    DROP COLUMN damage_resistance_laser,
    DROP COLUMN damage_resistance_fire,
    DROP COLUMN damage_resistance_plasma,
    DROP COLUMN damage_resistance_explosive,
    DROP COLUMN damage_resistance_electric;

-- ================================================
-- 4. Migration du weapon.damage_type ENUM vers FK
-- ================================================
-- Ajouter nouvelle colonne
ALTER TABLE weapon
    ADD COLUMN damage_type_id BIGINT REFERENCES damage_type (id);

-- Copier les anciennes données ENUM dans la FK
UPDATE weapon
SET damage_type_id = (SELECT id FROM damage_type WHERE code = weapon.damage_type::text);

-- Supprimer l'ancienne colonne ENUM
ALTER TABLE weapon
    DROP COLUMN damage_type;

-- ================================================
-- 5. Suppression du TYPE ENUM
-- ================================================
DROP TYPE IF EXISTS damage_type_old;
