-- Armes à énergie (ENERGY)
WITH inserted_energy AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path)
        VALUES ('WEAPON', '{
          "en": "Alien blaster",
          "fr": "Arme Alien"
        }',
                '{
                  "en": "A mysterious alien weapon.",
                  "fr": "Une mystérieuse arme alien."
                }',
                2, 10000, '/assets/weapons/alien_blaster.png'),
               ('WEAPON', '{
                 "en": "Laser pistol",
                 "fr": "Pistolet à laser"
               }',
                '{
                  "en": "A standard laser pistol.",
                  "fr": "Un pistolet laser standard."
                }',
                3, 1400, '/assets/weapons/laser_pistol.png'),
               ('WEAPON', '{
                 "en": "Plasma pistol",
                 "fr": "Pistolet à plasma"
               }',
                '{
                  "en": "A deadly plasma pistol.",
                  "fr": "Un mortel pistolet à plasma."
                }',
                4, 2750, '/assets/weapons/plasma_pistol.png'),
               ('WEAPON', '{
                 "en": "Laser rifle",
                 "fr": "Fusil à laser"
               }',
                '{
                  "en": "A powerful laser rifle.",
                  "fr": "Un puissant fusil à laser."
                }',
                6, 5000, '/assets/weapons/laser_rifle.png'),
               ('WEAPON', '{
                 "en": "Plasma rifle",
                 "fr": "Fusil à plasma"
               }',
                '{
                  "en": "A deadly plasma rifle.",
                  "fr": "Un mortel fusil à plasma."
                }',
                8, 4000, '/assets/weapons/plasma_rifle.png'),
               ('WEAPON', '{
                 "en": "Turbo plasma rifle",
                 "fr": "Fusil à turbo-plasma"
               }',
                '{
                  "en": "An enhanced plasma rifle.",
                  "fr": "Un fusil à plasma amélioré."
                }',
                8, 10000, '/assets/weapons/turbo_plasma_rifle.png'),
               ('WEAPON', '{
                 "en": "Gatling laser",
                 "fr": "Laser Gatling"
               }',
                '{
                  "en": "A devastating laser gatling.",
                  "fr": "Une dévastatrice laser Gatling."
                }',
                15, 7500, '/assets/weapons/gatling_laser.png')
        RETURNING id, names ->> 'en' AS name)
INSERT
INTO weapon (id, weapon_type, damage_type, required_strength, required_hands, capacity)
SELECT id,
       'ENERGY'::weapon_type,
       CASE name
           WHEN 'Alien blaster' THEN 'ELECTRICAL'::damage_type
           WHEN 'Laser pistol' THEN 'LASER'::damage_type
           WHEN 'Plasma pistol' THEN 'PLASMA'::damage_type
           WHEN 'Laser rifle' THEN 'LASER'::damage_type
           WHEN 'Plasma rifle' THEN 'PLASMA'::damage_type
           WHEN 'Turbo plasma rifle' THEN 'PLASMA'::damage_type
           WHEN 'Gatling laser' THEN 'LASER'::damage_type
           END,
       CASE name
           WHEN 'Alien blaster' THEN 2
           WHEN 'Laser pistol' THEN 4
           WHEN 'Plasma pistol' THEN 4
           WHEN 'Laser rifle' THEN 6
           WHEN 'Plasma rifle' THEN 6
           WHEN 'Turbo plasma rifle' THEN 6
           WHEN 'Gatling laser' THEN 6
           END,
       CASE name
           WHEN 'Alien blaster' THEN 1
           WHEN 'Laser pistol' THEN 1
           WHEN 'Plasma pistol' THEN 1
           WHEN 'Laser rifle' THEN 2
           WHEN 'Plasma rifle' THEN 2
           WHEN 'Turbo plasma rifle' THEN 2
           WHEN 'Gatling laser' THEN 2
           END,
       CASE name
           WHEN 'Alien blaster' THEN 30
           WHEN 'Laser pistol' THEN 12
           WHEN 'Plasma pistol' THEN 16
           WHEN 'Laser rifle' THEN 12
           WHEN 'Plasma rifle' THEN 10
           WHEN 'Turbo plasma rifle' THEN 10
           WHEN 'Gatling laser' THEN 30
           END
FROM inserted_energy;

-- Modes des armes à énergie
WITH weapon_ids AS (SELECT id, names ->> 'en' AS name
                    FROM item
                    WHERE names ->> 'en' IN (
                                             'Alien blaster', 'Laser pistol', 'Plasma pistol',
                                             'Laser rifle', 'Plasma rifle', 'Turbo plasma rifle', 'Gatling laser'
                        ))
INSERT
INTO weapon_mode (weapon_id, mode_type, min_damage, max_damage, range, action_points)
SELECT id, mode_type::weapon_mode_type, min_damage, max_damage, range, action_points
FROM weapon_ids,
     (VALUES ('Alien blaster', 'SINGLE', 30, 90, 10, 4),
             ('Alien blaster', 'AIMED', 30, 90, 10, 5),
             ('Laser pistol', 'SINGLE', 10, 22, 35, 5),
             ('Laser pistol', 'AIMED', 10, 22, 35, 6),
             ('Plasma pistol', 'SINGLE', 15, 35, 20, 5),
             ('Plasma pistol', 'AIMED', 15, 35, 20, 6),
             ('Laser rifle', 'SINGLE', 25, 50, 45, 5),
             ('Laser rifle', 'AIMED', 25, 50, 45, 6),
             ('Plasma rifle', 'SINGLE', 30, 65, 25, 5),
             ('Plasma rifle', 'AIMED', 30, 65, 25, 6),
             ('Turbo plasma rifle', 'SINGLE', 30, 70, 35, 4),
             ('Turbo plasma rifle', 'AIMED', 30, 70, 35, 5),
             ('Gatling laser', 'BURST', 20, 40, 45,
              6)) AS modes(weapon_name, mode_type, min_damage, max_damage, range, action_points)
WHERE name = weapon_name;
