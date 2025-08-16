-- Table: derived_stat
UPDATE derived_stat
SET formula = 'AGILITY + EQUIPPED_ARMOR_ARMOR_CLASS'
WHERE code = 'ARMOR_CLASS';