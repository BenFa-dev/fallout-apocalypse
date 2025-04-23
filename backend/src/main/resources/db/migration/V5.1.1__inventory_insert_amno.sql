WITH inserted_ammo AS (
    INSERT INTO item (type, names, descriptions, weight, base_price, path) VALUES
    ('AMMO', '{"en": ".223 FMJ", "fr": ".223 FMJ"}', 
           E'{"en": "Full Metal Jacket .223 caliber rounds", "fr": "Munitions blindées calibre .223"}', 
           2, 200, '/assets/ammo/223_fmj.png'),
    ('AMMO', '{"en": ".44 Magnum FMJ", "fr": ".44 Magnum FMJ"}',
           E'{"en": "Full Metal Jacket .44 Magnum rounds", "fr": "Munitions blindées calibre .44 Magnum"}',
           1, 50, '/assets/ammo/44_fmj.png'),
    ('AMMO', '{"en": ".44 Magnum JHP", "fr": ".44 Magnum JHP"}',
           E'{"en": "Jacketed Hollow Point .44 Magnum rounds", "fr": "Munitions expansives .44 Magnum"}',
           1, 50, '/assets/ammo/44_jhp.png'),
    ('AMMO', '{"en": "10mm AP", "fr": "10mm PA"}',
           E'{"en": "Armor Piercing 10mm rounds", "fr": "Munitions perforantes 10mm"}',
           1, 100, '/assets/ammo/10mm_ap.png'),
    ('AMMO', '{"en": "10mm JHP", "fr": "10mm JHP"}',
           E'{"en": "Jacketed Hollow Point 10mm rounds", "fr": "Munitions expansives 10mm"}',
           1, 75, '/assets/ammo/10mm_jhp.png'),
    ('AMMO', '{"en": "12 gauge shotgun shell", "fr": "Cartouche calibre 12"}',
           E'{"en": "12 gauge shotgun shells", "fr": "Cartouches pour fusil calibre 12"}',
           1, 225, '/assets/ammo/12gauge.png'),
    ('AMMO', '{"en": "14mm AP", "fr": "14mm PA"}',
           E'{"en": "14mm Armor Piercing rounds", "fr": "Munitions perforantes 14mm"}',
           1, 150, '/assets/ammo/14mm_ap.png'),
    ('AMMO', '{"en": "5mm AP", "fr": "5mm PA"}',
           E'{"en": "Armor Piercing 5mm rounds", "fr": "Munitions perforantes 5mm"}',
           1, 120, '/assets/ammo/5mm_ap.png'),
    ('AMMO', '{"en": "5mm JHP", "fr": "5mm JHP"}',
           E'{"en": "Jacketed Hollow Point 5mm rounds", "fr": "Munitions expansives 5mm"}',
           1, 100, '/assets/ammo/5mm_jhp.png'),
    ('AMMO', '{"en": "9mm ball", "fr": "9mm ball"}',
           E'{"en": "9mm rounds", "fr": "Munitions 9mm"}',
           2, 100, '/assets/ammo/9mm_ball.png'),
    ('AMMO', '{"en": "BBs", "fr": "BBs"}',
           E'{"en": "BB gun ammunition", "fr": "Munitions pour fusil à plomb"}',
           2, 20, '/assets/ammo/bbs.png'),
    ('AMMO', '{"en": "Explosive rocket", "fr": "Roquette explosive"}',
           E'{"en": "High explosive rocket", "fr": "Roquette hautement explosive"}',
           3, 200, '/assets/ammo/rocket.png'),
    ('AMMO', '{"en": "Rocket AP", "fr": "Roquette PA"}',
           E'{"en": "Armor piercing rocket", "fr": "Roquette perforante"}',
           3, 400, '/assets/ammo/rocket_ap.png'),
    ('AMMO', '{"en": "Flamethrower fuel", "fr": "Carburant lance-flammes"}',
           E'{"en": "Flamethrower fuel", "fr": "Carburant pour lance-flammes"}',
           10, 250, '/assets/ammo/fuel.png'),
    ('AMMO', E'{"en": "Small energy cell", "fr": "Pile à énergie"}',
           E'{"en": "Small energy cell for energy weapons", "fr": "Petite pile pour armes à énergie"}',
           3, 400, '/assets/ammo/small_energy_cell.png'),
    ('AMMO', E'{"en": "Microfusion cell", "fr": "Cellule à microfusion"}',
           E'{"en": "Microfusion cell for heavy energy weapons", "fr": "Cellule pour armes à énergie lourdes"}',
           5, 1000, '/assets/ammo/microfusion_cell.png')
    RETURNING id, names->>'en' AS name
)
INSERT INTO ammo (id, armor_class_modifier, damage_resistance_modifier, damage_modifier, damage_threshold_modifier)
SELECT id,
    CASE name
        WHEN '.223 FMJ' THEN -20
        WHEN '.44 Magnum FMJ' THEN 0
        WHEN '.44 Magnum JHP' THEN 0
        WHEN '10mm AP' THEN 0
        WHEN '10mm JHP' THEN 0
        WHEN '12 gauge shotgun shell' THEN -10
        WHEN '14mm AP' THEN 0
        WHEN '5mm AP' THEN 0
        WHEN '5mm JHP' THEN 0
        WHEN '9mm ball' THEN 0
        WHEN 'BBs' THEN 0
        WHEN 'Explosive rocket' THEN 0
        WHEN 'Rocket AP' THEN -15
        WHEN 'Flamethrower fuel' THEN -20
        WHEN 'Small energy cell' THEN 0
        WHEN 'Microfusion cell' THEN 0
    END,
    CASE name
        WHEN '.223 FMJ' THEN -20
        WHEN '.44 Magnum FMJ' THEN -20
        WHEN '.44 Magnum JHP' THEN 20
        WHEN '10mm AP' THEN -25
        WHEN '10mm JHP' THEN 25
        WHEN '12 gauge shotgun shell' THEN 0
        WHEN '14mm AP' THEN -50
        WHEN '5mm AP' THEN -35
        WHEN '5mm JHP' THEN 35
        WHEN '9mm ball' THEN 0
        WHEN 'BBs' THEN 0
        WHEN 'Explosive rocket' THEN -25
        WHEN 'Rocket AP' THEN -50
        WHEN 'Flamethrower fuel' THEN 25
        WHEN 'Small energy cell' THEN 0
        WHEN 'Microfusion cell' THEN 0
    END,
    CASE name
        WHEN '.223 FMJ' THEN 1
        WHEN '.44 Magnum FMJ' THEN 1
        WHEN '.44 Magnum JHP' THEN 2  -- Double damage
        WHEN '10mm AP' THEN 1
        WHEN '10mm JHP' THEN 2        -- Double damage
        WHEN '12 gauge shotgun shell' THEN 1
        WHEN '14mm AP' THEN 1
        WHEN '5mm AP' THEN 1
        WHEN '5mm JHP' THEN 2         -- Double damage
        WHEN '9mm ball' THEN 1
        WHEN 'BBs' THEN 1
        WHEN 'Explosive rocket' THEN 1
        WHEN 'Rocket AP' THEN 1
        WHEN 'Flamethrower fuel' THEN 1
        WHEN 'Small energy cell' THEN 1
        WHEN 'Microfusion cell' THEN 1
    END,
    CASE name
        WHEN '.223 FMJ' THEN 1
        WHEN '.44 Magnum FMJ' THEN 1
        WHEN '.44 Magnum JHP' THEN 1
        WHEN '10mm AP' THEN 2         -- Meilleure pénétration d'armure
        WHEN '10mm JHP' THEN 1
        WHEN '12 gauge shotgun shell' THEN 1
        WHEN '14mm AP' THEN 2         -- Meilleure pénétration d'armure
        WHEN '5mm AP' THEN 2          -- Meilleure pénétration d'armure
        WHEN '5mm JHP' THEN 1
        WHEN '9mm ball' THEN 1
        WHEN 'BBs' THEN 1
        WHEN 'Explosive rocket' THEN 1
        WHEN 'Rocket AP' THEN 2       -- Meilleure pénétration d'armure
        WHEN 'Flamethrower fuel' THEN 1
        WHEN 'Small energy cell' THEN 1
        WHEN 'Microfusion cell' THEN 1
    END
FROM inserted_ammo; 