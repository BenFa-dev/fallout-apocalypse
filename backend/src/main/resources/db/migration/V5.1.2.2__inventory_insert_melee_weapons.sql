-- Armes de mêlée (MELEE)
WITH inserted_melee AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path)
        VALUES ('WEAPON', '{
          "en": "Cattle prod",
          "fr": "Bâton d''élevage"
        }',
                '{
                  "en": "An electrical cattle prod.",
                  "fr": "Un bâton d''élevage électrique."
                }', 3, 600,
                '/assets/weapons/cattle_prod.png'),
               ('WEAPON', '{
                 "en": "Club",
                 "fr": "Matraque"
               }',
                '{
                  "en": "A simple wooden club.",
                  "fr": "Une simple matraque en bois."
                }', 3, 30,
                '/assets/weapons/club.png'),
               ('WEAPON', '{
                 "en": "Combat knife",
                 "fr": "Couteau de combat"
               }',
                '{
                  "en": "A sharp combat knife.",
                  "fr": "Un couteau de combat aiguisé."
                }', 2, 165,
                '/assets/weapons/combat_knife.png'),
               ('WEAPON', '{
                 "en": "Crowbar",
                 "fr": "Pince à levier"
               }',
                '{
                  "en": "A sturdy crowbar.",
                  "fr": "Une solide pince à levier."
                }', 5, 65,
                '/assets/weapons/crowbar.png'),
               ('WEAPON', '{
                 "en": "Knife",
                 "fr": "Couteau"
               }', '{
                 "en": "A simple knife.",
                 "fr": "Un simple couteau."
               }',
                1, 40, '/assets/weapons/knife.png'),
               ('WEAPON', '{
                 "en": "Ripper",
                 "fr": "Ripper"
               }',
                '{
                  "en": "A deadly chainsaw-like weapon.",
                  "fr": "Une mortelle arme de type tronçonneuse."
                }', 2, 900,
                '/assets/weapons/ripper.png'),
               ('WEAPON', '{
                 "en": "Sledgehammer",
                 "fr": "Massue"
               }',
                '{
                  "en": "A heavy sledgehammer.",
                  "fr": "Une lourde massue."
                }', 12, 120,
                '/assets/weapons/sledgehammer.png'),
               ('WEAPON', '{
                 "en": "Spear",
                 "fr": "Lance"
               }', '{
                 "en": "A long spear.",
                 "fr": "Une longue lance."
               }', 4, 80,
                '/assets/weapons/spear.png'),
               ('WEAPON', '{
                 "en": "Super sledge",
                 "fr": "SuperMassue"
               }',
                '{
                  "en": "A powerful sledgehammer.",
                  "fr": "Une puissante massue."
                }', 12, 3750,
                '/assets/weapons/super_sledge.png'),
               ('WEAPON', '{
                 "en": "Throwing knife",
                 "fr": "Couteau à lancer"
               }',
                '{
                  "en": "A balanced throwing knife.",
                  "fr": "Un couteau équilibré pour le lancer."
                }', 1, 100,
                '/assets/weapons/throwing_knife.png')
        RETURNING id, names ->> 'en' AS name)
INSERT
INTO weapon (id, weapon_type, damage_type, required_strength, required_hands, capacity)
SELECT id,
       'MELEE'::weapon_type,
       CASE name
           WHEN 'Cattle prod' THEN 'ELECTRICAL'::damage_type
           ELSE 'NORMAL'::damage_type
           END,
       CASE name
           WHEN 'Super sledge' THEN 5
           WHEN 'Sledgehammer' THEN 6
           WHEN 'Cattle prod' THEN 4
           WHEN 'Crowbar' THEN 5
           WHEN 'Spear' THEN 4
           ELSE 2
           END,
       CASE name
           WHEN 'Super sledge' THEN 2
           WHEN 'Sledgehammer' THEN 2
           WHEN 'Cattle prod' THEN 2
           WHEN 'Crowbar' THEN 2
           WHEN 'Spear' THEN 2
           ELSE 1
           END,
       CASE name
           WHEN 'Cattle prod' THEN 20
           WHEN 'Ripper' THEN 30
           END
FROM inserted_melee;

-- Insertion des modes d'armes de mêlée
WITH weapon_ids AS (SELECT id, names ->> 'en' AS name
                    FROM item
                    WHERE names ->> 'en' IN (
                                             'Cattle prod', 'Club', 'Combat knife', 'Crowbar', 'Knife',
                                             'Ripper', 'Sledgehammer', 'Spear', 'Super sledge', 'Throwing knife'
                        ))
INSERT
INTO weapon_mode (weapon_id, mode_type, min_damage, max_damage, range, action_points)
SELECT id, mode_type::weapon_mode_type, min_damage, max_damage, range, action_points
FROM weapon_ids,
     (VALUES ('Cattle prod', 'SWING', 12, 20, 1, 4),
             ('Cattle prod', 'THRUST', 12, 20, 1, 4),
             ('Club', 'SWING', 1, 6, 1, 3),
             ('Combat knife', 'SWING', 3, 10, 1, 3),
             ('Combat knife', 'THRUST', 3, 10, 1, 3),
             ('Crowbar', 'SWING', 3, 10, 1, 4),
             ('Knife', 'SWING', 1, 6, 1, 3),
             ('Knife', 'THRUST', 1, 6, 1, 3),
             ('Ripper', 'SWING', 15, 32, 1, 4),
             ('Ripper', 'THRUST', 15, 32, 1, 4),
             ('Sledgehammer', 'SWING', 4, 9, 2, 4),
             ('Sledgehammer', 'THRUST', 5, 5, 2, 5),
             ('Spear', 'THRUST', 3, 10, 2, 4),
             ('Spear', 'THROW', 3, 10, 8, 6),
             ('Super sledge', 'SWING', 18, 36, 2, 3),
             ('Super sledge', 'THRUST', 18, 36, 2, 4),
             ('Throwing knife', 'SWING', 3, 6, 1, 4),
             ('Throwing knife', 'THROW', 3, 6, 16,
              5)) AS modes(weapon_name, mode_type, min_damage, max_damage, range, action_points)
WHERE name = weapon_name;
