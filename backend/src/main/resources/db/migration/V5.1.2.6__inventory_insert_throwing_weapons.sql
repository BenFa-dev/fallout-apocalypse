-- Armes de lancer (THROWING)
WITH inserted_throwing AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path)
        VALUES ('WEAPON', '{
          "en": "Flare",
          "fr": "Fusée éclairante"
        }',
                '{
                  "en": "A bright signal flare.",
                  "fr": "Une fusée servant de signal lumineux."
                }',
                1, 35, '/assets/weapons/flare.png'),
               ('WEAPON', '{
                 "en": "Frag grenade",
                 "fr": "Grenade frag."
               }',
                '{
                  "en": "A deadly fragmentation grenade.",
                  "fr": "Une mortelle grenade à fragmentation."
                }',
                1, 150, '/assets/weapons/frag_grenade.png'),
               ('WEAPON', '{
                 "en": "Molotov cocktail",
                 "fr": "Cocktail Molotov"
               }',
                '{
                  "en": "An improvised incendiary device.",
                  "fr": "Un dispositif incendiaire improvisé."
                }',
                1, 50, '/assets/weapons/molotov.png'),
               ('WEAPON', '{
                 "en": "Plasma grenade",
                 "fr": "Grenade plasma"
               }',
                '{
                  "en": "A deadly plasma grenade.",
                  "fr": "Une mortelle grenade à plasma."
                }',
                1, 300, '/assets/weapons/plasma_grenade.png'),
               ('WEAPON', '{
                 "en": "Pulse grenade",
                 "fr": "Grenade à impulsions"
               }',
                '{
                  "en": "A powerful EMP grenade.",
                  "fr": "Une puissante grenade à impulsions."
                }',
                1, 250, '/assets/weapons/pulse_grenade.png')
        RETURNING id, names ->> 'en' AS name)
INSERT
INTO weapon (id, weapon_type, damage_type, required_strength, required_hands, capacity)
SELECT id,
       'THROWING'::weapon_type,
       CASE name
           WHEN 'Flare' THEN 'NORMAL'::damage_type
           WHEN 'Frag grenade' THEN 'EXPLOSION'::damage_type
           WHEN 'Molotov cocktail' THEN 'EXPLOSION'::damage_type
           WHEN 'Plasma grenade' THEN 'PLASMA'::damage_type
           WHEN 'Pulse grenade' THEN 'EMP'::damage_type
           END,
       CASE name
           WHEN 'Plasma grenade' THEN 4
           WHEN 'Pulse grenade' THEN 4
           ELSE 3
           END,
       1,
       1
FROM inserted_throwing;

-- Insertion des modes d'armes de lancer
WITH weapon_ids AS (SELECT id, names ->> 'en' AS name
                    FROM item
                    WHERE names ->> 'en' IN (
                                             'Flare', 'Frag grenade', 'Molotov cocktail', 'Plasma grenade',
                                             'Pulse grenade'
                        ))
INSERT
INTO weapon_mode (weapon_id, mode_type, min_damage, max_damage, range, action_points)
SELECT id, 'THROW'::weapon_mode_type, min_damage, max_damage, range, action_points
FROM weapon_ids,
     (VALUES ('Flare', 1, 1, 15, 1),
             ('Frag grenade', 20, 35, 15, 4),
             ('Molotov cocktail', 8, 20, 12, 5),
             ('Plasma grenade', 40, 90, 15, 4),
             ('Pulse grenade', 100, 150, 15, 4)) AS modes(weapon_name, min_damage, max_damage, range, action_points)
WHERE name = weapon_name;
