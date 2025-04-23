-- Insertion des munitions compatibles avec les armes
INSERT INTO weapon_compatible_ammo (weapon_id, ammo_id)
SELECT w.id, a.id
FROM weapon w
JOIN item wi ON w.id = wi.id
JOIN item a ON a.type = 'AMMO'
WHERE (wi.names->>'en' = 'Red Ryder LE BB gun' AND a.names->>'en' = 'BB')
   OR (wi.names->>'en' = 'Plasma grenade' AND a.names->>'en' = 'Microfusion cell')
   OR (wi.names->>'en' = 'Pulse grenade' AND a.names->>'en' = 'Microfusion cell');

INSERT INTO weapon_compatible_ammo (weapon_id, ammo_id)
SELECT w.id, a.id
FROM weapon w
JOIN item wi ON w.id = wi.id
JOIN ammo a ON 
    -- .223 pistol, Hunting rifle, Sniper rifle
    (wi.names->>'en' IN ('.223 pistol', 'Hunting rifle', 'Sniper rifle') AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = '.223 FMJ'
        )
    ))
    OR
    -- Desert Eagle .44
    (wi.names->>'en' = 'Desert Eagle .44' AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' IN ('.44 Magnum FMJ', '.44 Magnum JHP')
        )
    ))
    OR
    -- 10mm pistol, 10mm SMG
    (wi.names->>'en' IN ('10mm pistol', '10mm SMG') AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' IN ('10mm AP', '10mm JHP')
        )
    ))
    OR
    -- 14mm pistol
    (wi.names->>'en' = '14mm pistol' AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = '14mm AP'
        )
    ))
    OR
    -- 9mm Mauser
    (wi.names->>'en' = '9mm Mauser' AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = '9mm ball'
        )
    ))
    OR
    -- Combat shotgun, Shotgun
    (wi.names->>'en' IN ('Combat shotgun', 'Shotgun') AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = '12 gauge shotgun shell'
        )
    ))
    OR
    -- Assault rifle, Minigun
    (wi.names->>'en' IN ('Assault rifle', 'Minigun') AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' IN ('5mm AP', '5mm JHP')
        )
    ))
    OR
    -- Red Ryder BB gun, Red Ryder LE BB gun
    (wi.names->>'en' IN ('Red Ryder BB gun', 'Red Ryder LE BB gun') AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = 'BBs'
        )
    ))
    OR
    -- Rocket launcher
    (wi.names->>'en' = 'Rocket launcher' AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' IN ('Explosive rocket', 'Rocket AP')
        )
    ))
    OR
    -- Flamer
    (wi.names->>'en' = 'Flamer' AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = 'Flamethrower fuel'
        )
    ))
    OR
    -- Gatling laser, Laser rifle, Plasma rifle, Turbo plasma rifle
    (wi.names->>'en' IN ('Gatling laser', 'Laser rifle', 'Plasma rifle', 'Turbo plasma rifle') AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = 'Microfusion cell'
        )
    ))
    OR
    -- Alien blaster, Cattle prod, Laser pistol, Plasma pistol, Power fist, Ripper
    (wi.names->>'en' IN ('Alien blaster', 'Cattle prod', 'Laser pistol', 'Plasma pistol', 'Power fist', 'Ripper') AND a.id IN (
        SELECT id FROM ammo WHERE id IN (
            SELECT id FROM item WHERE names->>'en' = 'Small energy cell'
        )
    )); 