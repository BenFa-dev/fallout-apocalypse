-- Ajout de la colonne image_path dans la table skill
ALTER TABLE skill
    ADD COLUMN image_path VARCHAR(512);

-- Mise à jour des chemins d'images des skills
UPDATE skill
SET image_path = '/assets/skill/Barter.webp'
WHERE code = 'BARTER';
UPDATE skill
SET image_path = '/assets/skill/Big_Guns.webp'
WHERE code = 'BIG_GUNS';
UPDATE skill
SET image_path = '/assets/skill/Doctor.webp'
WHERE code = 'DOCTOR';
UPDATE skill
SET image_path = '/assets/skill/Energy_Weapons.webp'
WHERE code = 'ENERGY_WEAPONS';
UPDATE skill
SET image_path = '/assets/skill/First_Aid.webp'
WHERE code = 'FIRST_AID';
UPDATE skill
SET image_path = '/assets/skill/Gambling.webp'
WHERE code = 'GAMBLING';
UPDATE skill
SET image_path = '/assets/skill/Lockpick.webp'
WHERE code = 'LOCKPICK';
UPDATE skill
SET image_path = '/assets/skill/Melee_Weapons.webp'
WHERE code = 'MELEE_WEAPONS';
UPDATE skill
SET image_path = '/assets/skill/Outdoorsman.webp'
WHERE code = 'OUTDOORSMAN';
UPDATE skill
SET image_path = '/assets/skill/Repair.webp'
WHERE code = 'REPAIR';
UPDATE skill
SET image_path = '/assets/skill/Science.webp'
WHERE code = 'SCIENCE';
UPDATE skill
SET image_path = '/assets/skill/Small_Guns.webp'
WHERE code = 'SMALL_GUNS';
UPDATE skill
SET image_path = '/assets/skill/Sneak.webp'
WHERE code = 'SNEAK';
UPDATE skill
SET image_path = '/assets/skill/Speech.webp'
WHERE code = 'SPEECH';
UPDATE skill
SET image_path = '/assets/skill/Steal.webp'
WHERE code = 'STEAL';
UPDATE skill
SET image_path = '/assets/skill/Throwing.webp'
WHERE code = 'THROWING';
UPDATE skill
SET image_path = '/assets/skill/Traps.webp'
WHERE code = 'TRAPS';
UPDATE skill
SET image_path = '/assets/skill/Unarmed.webp'
WHERE code = 'UNARMED';
