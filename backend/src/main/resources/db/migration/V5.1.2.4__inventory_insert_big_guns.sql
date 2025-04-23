-- Armes lourdes (BIG_GUN)
WITH inserted_big_guns AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path)
        VALUES ('WEAPON', '{
          "en": "Minigun",
          "fr": "Mini-canon"
        }',
                '{
                  "en": "A devastating rotary cannon.",
                  "fr": "Une dévastatrice mitrailleuse rotative."
                }',
                28, 3800, '/assets/weapons/minigun.png'),
               ('WEAPON', '{
                 "en": "Flamer",
                 "fr": "Lance-flammes"
               }',
                '{
                  "en": "A deadly flamethrower.",
                  "fr": "Un mortel lance-flammes."
                }',
                28, 2000, '/assets/weapons/flamer.png'),
               ('WEAPON', '{
                 "en": "Rocket launcher",
                 "fr": "Lance-roquettes"
               }',
                '{
                  "en": "A powerful rocket launcher.",
                  "fr": "Un puissant lance-roquettes."
                }',
                15, 2300, '/assets/weapons/rocket_launcher.png')
        RETURNING id, names ->> 'en' AS name)
INSERT
INTO weapon (id, weapon_type, damage_type, required_strength, required_hands, capacity)
SELECT id,
       'BIG_GUN'::weapon_type,
       CASE name
           WHEN 'Flamer' THEN 'FIRE'::damage_type
           WHEN 'Rocket launcher' THEN 'EXPLOSION'::damage_type
           ELSE 'NORMAL'::damage_type
           END,
       CASE name
           WHEN 'Minigun' THEN 7
           WHEN 'Flamer' THEN 6
           WHEN 'Rocket launcher' THEN 6
           END,
       2,
       CASE name
           WHEN 'Minigun' THEN 120
           WHEN 'Flamer' THEN 5
           WHEN 'Rocket launcher' THEN 1
           END
FROM inserted_big_guns;

-- Insertion des modes d'armes lourdes
WITH weapon_ids AS (SELECT id, names ->> 'en' AS name
                    FROM item
                    WHERE names ->> 'en' IN ('Minigun', 'Flamer', 'Rocket launcher'))
INSERT
INTO weapon_mode (weapon_id, mode_type, min_damage, max_damage, range, action_points)
SELECT id, mode_type::weapon_mode_type, min_damage, max_damage, range, action_points
FROM weapon_ids,
     (VALUES ('Minigun', 'BURST', 7, 11, 35, 6),
             ('Flamer', 'BURST', 40, 90, 5, 6),
             ('Rocket launcher', 'SINGLE', 35, 100, 40,
              6)) AS modes(weapon_name, mode_type, min_damage, max_damage, range, action_points)
WHERE name = weapon_name;
