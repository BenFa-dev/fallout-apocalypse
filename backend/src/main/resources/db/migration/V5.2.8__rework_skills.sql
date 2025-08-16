-- Ajout de la colonne 'formula' à la table skill
ALTER TABLE skill
    ADD COLUMN formula TEXT;

-- Mise à jour des formules des compétences
UPDATE skill
SET formula = '35 + 1 * AGILITY'
WHERE code = 'SMALL_GUNS';
UPDATE skill
SET formula = '10 + 1 * AGILITY'
WHERE code = 'BIG_GUNS';
UPDATE skill
SET formula = '10 + 1 * AGILITY'
WHERE code = 'ENERGY_WEAPONS';
UPDATE skill
SET formula = '65 + 1 * ((AGILITY + STRENGTH) / 2)'
WHERE code = 'UNARMED';
UPDATE skill
SET formula = '55 + 1 * ((STRENGTH + AGILITY) / 2)'
WHERE code = 'MELEE_WEAPONS';
UPDATE skill
SET formula = '40 + 1 * AGILITY'
WHERE code = 'THROWING';
UPDATE skill
SET formula = '30 + 1 * ((PERCEPTION + INTELLIGENCE) / 2)'
WHERE code = 'FIRST_AID';
UPDATE skill
SET formula = '15 + 1 * ((PERCEPTION + INTELLIGENCE) / 2)'
WHERE code = 'DOCTOR';
UPDATE skill
SET formula = '25 + 1 * AGILITY'
WHERE code = 'SNEAK';
UPDATE skill
SET formula = '20 + 1 * ((PERCEPTION + AGILITY) / 2)'
WHERE code = 'LOCKPICK';
UPDATE skill
SET formula = '20 + 1 * AGILITY'
WHERE code = 'STEAL';
UPDATE skill
SET formula = '20 + 1 * ((PERCEPTION + AGILITY) / 2)'
WHERE code = 'TRAPS';
UPDATE skill
SET formula = '25 + 2 * INTELLIGENCE'
WHERE code = 'SCIENCE';
UPDATE skill
SET formula = '20 + 1 * INTELLIGENCE'
WHERE code = 'REPAIR';
UPDATE skill
SET formula = '25 + 2 * CHARISMA'
WHERE code = 'SPEECH';
UPDATE skill
SET formula = '20 + 2 * CHARISMA'
WHERE code = 'BARTER';
UPDATE skill
SET formula = '20 + 3 * LUCK'
WHERE code = 'GAMBLING';
UPDATE skill
SET formula = '5 + 1 * ((INTELLIGENCE + ENDURANCE) / 2)'
WHERE code = 'OUTDOORSMAN';