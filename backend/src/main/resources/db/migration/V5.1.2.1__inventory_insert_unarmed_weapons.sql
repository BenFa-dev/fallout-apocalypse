-- Armes à mains nues (UNARMED)
WITH inserted_unarmed AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path)
        VALUES ('WEAPON', '{"en": "Brass knuckles", "fr": "Protections cuivrées"}',
                '{"en": "Metal knuckles that enhance unarmed combat.", "fr": "Protections en métal qui améliorent le combat à mains nues."}',
                1, 40, '/assets/weapons/brass_knuckles.png'),
               ('WEAPON', '{"en": "Power fist", "fr": "SuperPoing"}',
                '{"en": "A powerful energy-enhanced fist weapon.", "fr": "Une puissante arme de poing améliorée par énergie."}',
                7, 2200, '/assets/weapons/power_fist.png'),
               ('WEAPON', '{"en": "Rock", "fr": "Rocher"}',
                '{"en": "A simple rock that can be used as a weapon.", "fr": "Une simple pierre qui peut être utilisée comme arme."}',
                1, 0, '/assets/weapons/rock.png'),
               ('WEAPON', '{"en": "Spiked knuckles", "fr": "Protections aiguisées"}',
                '{"en": "Knuckles with sharp spikes for increased damage.", "fr": "Protections avec des pointes acérées pour augmenter les dégâts."}',
                1, 250, '/assets/weapons/spiked_knuckles.png')
        RETURNING id, names ->> 'en' AS name)

INSERT
INTO weapon (id, weapon_type, damage_type, required_strength, required_hands, capacity)
SELECT id,
       'UNARMED'::weapon_type,
       CASE name
           WHEN 'Power fist' THEN 'ELECTRICAL'::damage_type
           ELSE 'NORMAL'::damage_type
           END,
       1,
       1,
       CASE name
           WHEN 'Power fist' THEN 25
           END
FROM inserted_unarmed;

-- Insertion des modes d'armes à mains nues (PUNCH)
INSERT INTO weapon_mode (weapon_id, mode_type, min_damage, max_damage, range, action_points)
SELECT w.id,
       'PUNCH'::weapon_mode_type,
       CASE i.names ->> 'en'
           WHEN 'Brass knuckles' THEN 2
           WHEN 'Power fist' THEN 12
           WHEN 'Rock' THEN 1
           WHEN 'Spiked knuckles' THEN 4
           END,
       CASE i.names ->> 'en'
           WHEN 'Brass knuckles' THEN 5
           WHEN 'Power fist' THEN 24
           WHEN 'Rock' THEN 4
           WHEN 'Spiked knuckles' THEN 10
           END,
       1, -- Always melee range
       3
FROM weapon w
         JOIN item i ON w.id = i.id
WHERE w.weapon_type = 'UNARMED';

-- Ajout du mode THROW pour Rock
INSERT INTO weapon_mode (weapon_id, mode_type, min_damage, max_damage, range, action_points)
SELECT w.id,
       'THROW'::weapon_mode_type,
       1,
       4,
       15,
       4
FROM weapon w
         JOIN item i ON w.id = i.id
WHERE i.names ->> 'en' = 'Rock';
