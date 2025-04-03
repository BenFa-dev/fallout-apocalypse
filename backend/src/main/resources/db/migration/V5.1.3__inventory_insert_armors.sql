-- Insertion des armures
WITH inserted_armors AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path)
        VALUES ('ARMOR',
                '{"en": "Leather armor", "fr": "Armure en cuir"}',
                '{"en": "A suit of leather armor. Provides basic protection against physical damage.", "fr": "Une armure en cuir. Offre une protection basique contre les dégâts physiques."}',
                8.0, 800, 'items/armor/leather_armor.png'),
               ('ARMOR',
                '{"en": "Leather jacket", "fr": "Veste en cuir"}',
                '{"en": "A leather jacket. Provides minimal protection but is light and comfortable.", "fr": "Une veste en cuir. Offre une protection minimale mais est légère et confortable."}',
                5.0, 300, 'items/armor/leather_jacket.png'),
               ('ARMOR',
                '{"en": "Metal armor", "fr": "Armure métallique"}',
                '{"en": "A suit of metal armor. Provides good protection against physical damage.", "fr": "Une armure métallique. Offre une bonne protection contre les dégâts physiques."}',
                35.0, 1100, 'items/armor/metal_armor.png'),
               ('ARMOR',
                '{"en": "Combat armor", "fr": "Armure de combat"}',
                '{"en": "A suit of combat armor. Provides excellent protection against physical damage.", "fr": "Une armure de combat. Offre une excellente protection contre les dégâts physiques."}',
                20.0, 6500, 'items/armor/combat_armor.png'),
               ('ARMOR',
                '{"en": "Power armor", "fr": "Armure assistée"}',
                '{"en": "A suit of power armor. Provides maximum protection against all types of damage.", "fr": "Une armure assistée. Offre une protection maximale contre tous les types de dégâts."}',
                85.0, 12500, 'items/armor/power_armor.png'),
               ('ARMOR',
                '{"en": "Hardened power armor", "fr": "Armure assistée renforcée"}',
                '{"en": "A reinforced suit of power armor. Provides even better protection than standard power armor.", "fr": "Une armure assistée renforcée. Offre une protection encore meilleure que l''armure assistée standard."}',
                100.0, 14500, 'items/armor/hardened_power_armor.png'),
               ('ARMOR',
                '{"en": "Tesla armor", "fr": "Armure Tesla"}',
                '{"en": "A suit of Tesla armor. Provides excellent protection against energy weapons.", "fr": "Une armure Tesla. Offre une excellente protection contre les armes à énergie."}',
                35.0, 4200, 'items/armor/tesla_armor.png'),
               ('ARMOR',
                '{"en": "Brotherhood armor", "fr": "Armure de la Confrérie"}',
                '{"en": "A suit of Brotherhood armor. Provides excellent all-around protection.", "fr": "Une armure de la Confrérie. Offre une excellente protection polyvalente."}',
                25.0, 4800, 'items/armor/brotherhood_armor.png'),
               ('ARMOR',
                '{"en": "Robes", "fr": "Robe"}',
                '{"en": "A simple robe. Provides minimal protection but is comfortable.", "fr": "Une robe simple. Offre une protection minimale mais est confortable."}',
                10.0, 90, 'items/armor/robes.png')
        RETURNING id, names->>'en' AS name)
INSERT
INTO armor (id,
            armor_class,
            damage_threshold_normal,
            damage_threshold_laser,
            damage_threshold_fire,
            damage_threshold_plasma,
            damage_threshold_explosive,
            damage_threshold_electric,
            damage_resistance_normal,
            damage_resistance_laser,
            damage_resistance_fire,
            damage_resistance_plasma,
            damage_resistance_explosive,
            damage_resistance_electric)
SELECT id,
       CASE
           WHEN name = 'Leather armor' THEN 15
           WHEN name = 'Leather jacket' THEN 8
           WHEN name = 'Metal armor' THEN 10
           WHEN name = 'Combat armor' THEN 20
           WHEN name = 'Power armor' THEN 25
           WHEN name = 'Hardened power armor' THEN 25
           WHEN name = 'Tesla armor' THEN 15
           WHEN name = 'Brotherhood armor' THEN 20
           WHEN name = 'Robes' THEN 5
           END,
       CASE
           WHEN name = 'Leather armor' THEN 2
           WHEN name = 'Leather jacket' THEN 0
           WHEN name = 'Metal armor' THEN 4
           WHEN name = 'Combat armor' THEN 5
           WHEN name = 'Power armor' THEN 12
           WHEN name = 'Hardened power armor' THEN 13
           WHEN name = 'Tesla armor' THEN 4
           WHEN name = 'Brotherhood armor' THEN 8
           WHEN name = 'Robes' THEN 0
           END,
       CASE
           WHEN name = 'Leather armor' THEN 0
           WHEN name = 'Leather jacket' THEN 0
           WHEN name = 'Metal armor' THEN 6
           WHEN name = 'Combat armor' THEN 8
           WHEN name = 'Power armor' THEN 18
           WHEN name = 'Hardened power armor' THEN 19
           WHEN name = 'Tesla armor' THEN 19
           WHEN name = 'Brotherhood armor' THEN 8
           WHEN name = 'Robes' THEN 0
           END,
       CASE
           WHEN name = 'Leather armor' THEN 0
           WHEN name = 'Leather jacket' THEN 0
           WHEN name = 'Metal armor' THEN 4
           WHEN name = 'Combat armor' THEN 4
           WHEN name = 'Power armor' THEN 12
           WHEN name = 'Hardened power armor' THEN 12
           WHEN name = 'Tesla armor' THEN 4
           WHEN name = 'Brotherhood armor' THEN 7
           WHEN name = 'Robes' THEN 0
           END,
       CASE
           WHEN name = 'Leather armor' THEN 0
           WHEN name = 'Leather jacket' THEN 0
           WHEN name = 'Metal armor' THEN 4
           WHEN name = 'Combat armor' THEN 4
           WHEN name = 'Power armor' THEN 10
           WHEN name = 'Hardened power armor' THEN 13
           WHEN name = 'Tesla armor' THEN 10
           WHEN name = 'Brotherhood armor' THEN 7
           WHEN name = 'Robes' THEN 0
           END,
       CASE
           WHEN name = 'Leather armor' THEN 0
           WHEN name = 'Leather jacket' THEN 0
           WHEN name = 'Metal armor' THEN 4
           WHEN name = 'Combat armor' THEN 6
           WHEN name = 'Power armor' THEN 20
           WHEN name = 'Hardened power armor' THEN 20
           WHEN name = 'Tesla armor' THEN 4
           WHEN name = 'Brotherhood armor' THEN 8
           WHEN name = 'Robes' THEN 0
           END,
       CASE
           WHEN name = 'Leather armor' THEN 0
           WHEN name = 'Leather jacket' THEN 0
           WHEN name = 'Metal armor' THEN 0
           WHEN name = 'Combat armor' THEN 2
           WHEN name = 'Power armor' THEN 12
           WHEN name = 'Hardened power armor' THEN 13
           WHEN name = 'Tesla armor' THEN 12
           WHEN name = 'Brotherhood armor' THEN 6
           WHEN name = 'Robes' THEN 0
           END,
       CASE
           WHEN name = 'Leather armor' THEN 25
           WHEN name = 'Leather jacket' THEN 20
           WHEN name = 'Metal armor' THEN 30
           WHEN name = 'Combat armor' THEN 40
           WHEN name = 'Power armor' THEN 40
           WHEN name = 'Hardened power armor' THEN 50
           WHEN name = 'Tesla armor' THEN 20
           WHEN name = 'Brotherhood armor' THEN 40
           WHEN name = 'Robes' THEN 20
           END,
       CASE
           WHEN name = 'Leather armor' THEN 20
           WHEN name = 'Leather jacket' THEN 20
           WHEN name = 'Metal armor' THEN 75
           WHEN name = 'Combat armor' THEN 60
           WHEN name = 'Power armor' THEN 80
           WHEN name = 'Hardened power armor' THEN 90
           WHEN name = 'Tesla armor' THEN 90
           WHEN name = 'Brotherhood armor' THEN 70
           WHEN name = 'Robes' THEN 25
           END,
       CASE
           WHEN name = 'Leather armor' THEN 20
           WHEN name = 'Leather jacket' THEN 10
           WHEN name = 'Metal armor' THEN 10
           WHEN name = 'Combat armor' THEN 30
           WHEN name = 'Power armor' THEN 60
           WHEN name = 'Hardened power armor' THEN 60
           WHEN name = 'Tesla armor' THEN 20
           WHEN name = 'Brotherhood armor' THEN 50
           WHEN name = 'Robes' THEN 10
           END,
       CASE
           WHEN name = 'Leather armor' THEN 20
           WHEN name = 'Leather jacket' THEN 10
           WHEN name = 'Metal armor' THEN 20
           WHEN name = 'Combat armor' THEN 50
           WHEN name = 'Power armor' THEN 40
           WHEN name = 'Hardened power armor' THEN 50
           WHEN name = 'Tesla armor' THEN 80
           WHEN name = 'Brotherhood armor' THEN 60
           WHEN name = 'Robes' THEN 10
           END,
       CASE
           WHEN name = 'Leather armor' THEN 20
           WHEN name = 'Leather jacket' THEN 10
           WHEN name = 'Metal armor' THEN 25
           WHEN name = 'Combat armor' THEN 40
           WHEN name = 'Power armor' THEN 50
           WHEN name = 'Hardened power armor' THEN 60
           WHEN name = 'Tesla armor' THEN 10
           WHEN name = 'Brotherhood armor' THEN 40
           WHEN name = 'Robes' THEN 20
           END,
       CASE
           WHEN name = 'Leather armor' THEN 30
           WHEN name = 'Leather jacket' THEN 30
           WHEN name = 'Metal armor' THEN 0
           WHEN name = 'Combat armor' THEN 50
           WHEN name = 'Power armor' THEN 40
           WHEN name = 'Hardened power armor' THEN 50
           WHEN name = 'Tesla armor' THEN 80
           WHEN name = 'Brotherhood armor' THEN 60
           WHEN name = 'Robes' THEN 40
           END
FROM inserted_armors; 