-- Armes légères (SMALL_GUN)
WITH inserted_small_guns AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path)
        VALUES ('WEAPON', '{
          "en": "9mm Mauser",
          "fr": "Mauser 9 mm"
        }',
                '{
                  "en": "A reliable 9mm pistol.",
                  "fr": "Un pistolet 9 mm fiable."
                }', 3, 1500, '/assets/weapons/9mm_mauser.png'),
               ('WEAPON', '{
                 "en": "10mm pistol",
                 "fr": "Pistolet 10 mm"
               }',
                '{
                  "en": "A standard 10mm pistol.",
                  "fr": "Un pistolet 10 mm standard."
                }', 3, 250, '/assets/weapons/10mm_pistol.png'),
               ('WEAPON', '{
                 "en": "Desert Eagle .44",
                 "fr": "Aigle du désert .44"
               }',
                '{
                  "en": "A powerful .44 Magnum pistol.",
                  "fr": "Un puissant pistolet .44 Magnum."
                }', 4, 800, '/assets/weapons/desert_eagle.png'),
               ('WEAPON', '{
                 "en": ".223 pistol",
                 "fr": "Pistolet .223"
               }',
                '{
                  "en": "A powerful pistol chambered in .223.",
                  "fr": "Un puissant pistolet chambré en .223."
                }', 5, 3500, '/assets/weapons/223_pistol.png'),
               ('WEAPON', '{
                 "en": "14mm pistol",
                 "fr": "Pistolet 14 mm"
               }',
                '{
                  "en": "A deadly 14mm pistol.",
                  "fr": "Un mortel pistolet 14 mm."
                }', 4, 1100, '/assets/weapons/14mm_pistol.png'),
               ('WEAPON', '{
                 "en": "Assault rifle",
                 "fr": "Fusil d''assaut"
               }',
                '{
                  "en": "A versatile assault rifle.",
                  "fr": "Un fusil d''assaut polyvalent."
                }', 7, 1300, '/assets/weapons/assault_rifle.png'),
               ('WEAPON', '{
                 "en": "Hunting rifle",
                 "fr": "Carabine"
               }',
                '{
                  "en": "A reliable hunting rifle.",
                  "fr": "Une carabine fiable."
                }', 9, 1000, '/assets/weapons/hunting_rifle.png'),
               ('WEAPON', '{
                 "en": "Red Ryder BB gun",
                 "fr": "Arme Red Ryder BB"
               }',
                '{
                  "en": "A classic BB gun.",
                  "fr": "Une classique arme à plombs."
                }', 5, 200, '/assets/weapons/red_ryder_bb.png'),
               ('WEAPON', '{
                 "en": "Red Ryder LE BB gun",
                 "fr": "Arme Red Ryder LE BB"
               }',
                '{
                  "en": "A limited edition BB gun.",
                  "fr": "Une arme à plombs édition limitée."
                }', 5, 3500, '/assets/weapons/red_ryder_le_bb.png'),
               ('WEAPON', '{
                 "en": "Sniper rifle",
                 "fr": "Fusil d''embuscade"
               }',
                '{
                  "en": "A deadly sniper rifle.",
                  "fr": "Un mortel fusil d''embuscade."
                }', 10, 2200, '/assets/weapons/sniper_rifle.png'),
               ('WEAPON', '{
                 "en": "Combat shotgun",
                 "fr": "Fusil de combat"
               }',
                '{
                  "en": "A powerful combat shotgun.",
                  "fr": "Un puissant fusil de combat."
                }', 10, 2750, '/assets/weapons/combat_shotgun.png'),
               ('WEAPON', '{
                 "en": "Shotgun",
                 "fr": "Fusil de chasse"
               }',
                '{
                  "en": "A standard shotgun.",
                  "fr": "Un fusil de chasse standard."
                }', 5, 800, '/assets/weapons/shotgun.png'),
               ('WEAPON', '{
                 "en": "10mm SMG",
                 "fr": "Mitraillette 10 mm"
               }',
                '{
                  "en": "A rapid-fire 10mm submachine gun.",
                  "fr": "Une mitraillette 10 mm à tir rapide."
                }', 5, 1000, '/assets/weapons/10mm_smg.png')
        RETURNING id, names ->> 'en' AS name)
INSERT
INTO weapon (id, weapon_type, damage_type, required_strength, required_hands, capacity)
SELECT id,
       'SMALL_GUN'::weapon_type,
       'NORMAL'::damage_type,
       CASE name
           WHEN 'Assault rifle' THEN 5
           WHEN 'Hunting rifle' THEN 5
           WHEN 'Sniper rifle' THEN 5
           WHEN 'Combat shotgun' THEN 5
           WHEN 'Shotgun' THEN 4
           WHEN '10mm SMG' THEN 4
           ELSE 3
           END,
       CASE name
           WHEN 'Assault rifle' THEN 2
           WHEN 'Hunting rifle' THEN 2
           WHEN 'Sniper rifle' THEN 2
           WHEN 'Combat shotgun' THEN 2
           WHEN 'Shotgun' THEN 2
           WHEN '10mm SMG' THEN 1
           ELSE 1
           END,
       CASE name
           WHEN '9mm Mauser' THEN 7
           WHEN '10mm pistol' THEN 12
           WHEN 'Desert Eagle .44' THEN 8
           WHEN '.223 pistol' THEN 5
           WHEN '14mm pistol' THEN 6
           WHEN 'Assault rifle' THEN 24
           WHEN 'Hunting rifle' THEN 10
           WHEN 'Red Ryder BB gun' THEN 100
           WHEN 'Red Ryder LE BB gun' THEN 100
           WHEN 'Sniper rifle' THEN 6
           WHEN 'Combat shotgun' THEN 12
           WHEN 'Shotgun' THEN 2
           WHEN '10mm SMG' THEN 30
           END
FROM inserted_small_guns;

-- Insertion des modes
WITH weapon_ids AS (SELECT id, names ->> 'en' AS name
                    FROM item
                    WHERE names ->> 'en' IN (
                                             '9mm Mauser', '10mm pistol', 'Desert Eagle .44', '.223 pistol',
                                             '14mm pistol',
                                             'Assault rifle', 'Hunting rifle', 'Red Ryder BB gun',
                                             'Red Ryder LE BB gun',
                                             'Sniper rifle', 'Combat shotgun', 'Shotgun', '10mm SMG'
                        ))
INSERT
INTO weapon_mode (weapon_id, mode_type, min_damage, max_damage, range, action_points)
SELECT id, mode_type::weapon_mode_type, min_damage, max_damage, range, action_points
FROM weapon_ids,
     (VALUES ('10mm SMG', 'SINGLE', 5, 12, 25, 5),
             ('10mm SMG', 'AIMED', 5, 12, 25, 6),
             ('10mm SMG', 'BURST', 5, 12, 20, 6),
             ('9mm Mauser', 'SINGLE', 5, 10, 22, 4),
             ('9mm Mauser', 'AIMED', 5, 10, 22, 5),
             ('10mm pistol', 'SINGLE', 5, 12, 25, 4),
             ('10mm pistol', 'AIMED', 5, 12, 25, 6),
             ('Desert Eagle .44', 'SINGLE', 10, 16, 25, 4),
             ('Desert Eagle .44', 'AIMED', 10, 16, 25, 6),
             ('.223 pistol', 'SINGLE', 20, 30, 25, 4),
             ('.223 pistol', 'AIMED', 20, 30, 25, 6),
             ('14mm pistol', 'SINGLE', 12, 22, 25, 4),
             ('14mm pistol', 'AIMED', 12, 22, 25, 6),
             ('Assault rifle', 'SINGLE', 8, 16, 45, 5),
             ('Assault rifle', 'AIMED', 8, 16, 45, 6),
             ('Assault rifle', 'BURST', 8, 16, 38, 6),
             ('Hunting rifle', 'SINGLE', 8, 20, 40, 5),
             ('Hunting rifle', 'AIMED', 8, 20, 40, 6),
             ('Red Ryder BB gun', 'SINGLE', 1, 3, 25, 4),
             ('Red Ryder BB gun', 'AIMED', 1, 3, 25, 5),
             ('Red Ryder LE BB gun', 'SINGLE', 25, 25, 25, 4),
             ('Red Ryder LE BB gun', 'AIMED', 25, 25, 25, 5),
             ('Sniper rifle', 'SINGLE', 14, 34, 50, 6),
             ('Sniper rifle', 'AIMED', 14, 34, 50, 7),
             ('Combat shotgun', 'SINGLE', 15, 25, 22, 5),
             ('Combat shotgun', 'AIMED', 15, 25, 22, 6),
             ('Combat shotgun', 'BURST', 15, 25, 18, 6),
             ('Shotgun', 'SINGLE', 12, 22, 14, 5),
             ('Shotgun', 'AIMED', 12, 22, 14,
              6)) AS modes(weapon_name, mode_type, min_damage, max_damage, range, action_points)
WHERE name = weapon_name;
