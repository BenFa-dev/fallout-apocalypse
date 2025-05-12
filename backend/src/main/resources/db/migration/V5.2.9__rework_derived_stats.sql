-- Table: derived_stat
CREATE TABLE derived_stat
(
    id            BIGSERIAL PRIMARY KEY,
    code          VARCHAR(64)  NOT NULL UNIQUE,
    names         JSONB        NOT NULL, -- {"en": "Action Points", "fr": "Points d'action"}
    descriptions  JSONB        NOT NULL,
    image_path    VARCHAR(512) NOT NULL,
    display_order INTEGER      NOT NULL DEFAULT 0,
    visible       BOOLEAN      NOT NULL DEFAULT true,
    formula       TEXT,
    created_at    TIMESTAMPTZ           DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMPTZ           DEFAULT CURRENT_TIMESTAMP
);

-- Données avec formules directement dans la colonne 'formula'
INSERT INTO derived_stat (code, names, descriptions, image_path, display_order, visible, formula)
VALUES ('HIT_POINTS', '{
  "en": "Hit Points",
  "fr": "Points de vie"
}', '{
  "en": "How much damage your character can take before dying.",
  "fr": "Quantité de dégâts qu''un personnage peut subir avant de mourir."
}', '/assets/derivedStat/Hit_Points.webp', 0, true, '15 + (2 * ENDURANCE) + STRENGTH'),

       ('ARMOR_CLASS', '{
         "en": "Armor Class",
         "fr": "Classe d''armure"
       }', '{
         "en": "How good your character is at avoiding being hit in combat.",
         "fr": "Aptitude à éviter les coups pendant un combat."
       }', '/assets/derivedStat/Armor_Class.webp', 1, true, 'AGILITY'),

       ('ACTION_POINTS', '{
         "en": "Action Points",
         "fr": "Points d''action"
       }', '{
         "en": "The amount of actions your character can take during a combat turn.",
         "fr": "Nombre d''actions réalisables pendant un tour de combat."
       }', '/assets/derivedStat/Action_Points.webp', 2, true, '(AGILITY / 2) + 5'),

       ('CARRY_WEIGHT', '{
         "en": "Carry Weight",
         "fr": "Poids transportable"
       }', '{
         "en": "The maximum amount of equipment your character can carry, in pounds.",
         "fr": "Quantité maximale d''équipement transportable (en livres)."
       }', '/assets/derivedStat/Carry_Weight.webp', 3, true, '(STRENGTH + 1) * 25'),

       ('MELEE_DAMAGE', '{
         "en": "Melee Damage",
         "fr": "Dégâts de mêlée"
       }', '{
         "en": "Amount of bonus damage in hand-to-hand combat.",
         "fr": "Bonus de dégâts en combat rapproché."
       }', '/assets/derivedStat/Melee_Damage.webp', 4, true, 'max(1, STRENGTH - 5)'),

       ('DAMAGE_RESISTANCE', '{
         "en": "Damage Resistance",
         "fr": "Résistance aux dégâts"
       }', '{
         "en": "Any damage taken is reduced by this amount.",
         "fr": "Réduction de tous les dégâts reçus."
       }', '/assets/derivedStat/Damage_Resistance.webp', 5, true, '0'),

       ('POISON_RESISTANCE', '{
         "en": "Poison Resistance",
         "fr": "Résistance au poison"
       }', '{
         "en": "Reduces any poison damage by this amount.",
         "fr": "Réduit les dégâts de poison reçus."
       }', '/assets/derivedStat/Poison_Resistance.webp', 6, true, 'ENDURANCE * 5'),

       ('RADIATION_RESISTANCE', '{
         "en": "Radiation Resistance",
         "fr": "Résistance aux radiations"
       }', '{
         "en": "Amount of radiation your character is exposed to is reduced by this amount.",
         "fr": "Réduction de l''exposition aux radiations."
       }', '/assets/derivedStat/Radiation_Resistance.webp', 7, true, 'ENDURANCE * 2'),

       ('SEQUENCE', '{
         "en": "Sequence",
         "fr": "Initiative"
       }', '{
         "en": "Determines how soon in a combat turn your character can react.",
         "fr": "Détermine l''ordre d''action lors d''un tour de combat."
       }', '/assets/derivedStat/Sequence.webp', 8, true, 'PERCEPTION * 2'),

       ('HEALING_RATE', '{
         "en": "Healing Rate",
         "fr": "Taux de régénération"
       }', '{
         "en": "How much Hit Points you heal at the end of each day.",
         "fr": "Nombre de PV régénérés à la fin de chaque journée."
       }', '/assets/derivedStat/Healing_Rate.webp', 9, true, 'max(1, ENDURANCE / 3)'),

       ('CRITICAL_CHANCE', '{
         "en": "Critical Chance",
         "fr": "Chance de critique"
       }', '{
         "en": "Chance to cause a Critical Hit.",
         "fr": "Probabilité d''infliger un coup critique."
       }', '/assets/derivedStat/Critical_Chance.webp', 10, true, 'LUCK'),

       ('PERK_RATE', '{
         "en": "Perk Rate",
         "fr": "Fréquence des perks"
       }', '{
         "en": "How many levels must pass before your character gains another perk.",
         "fr": "Nombre de niveaux nécessaires avant l''obtention d''un perk supplémentaire."
       }', '/assets/derivedStat/Perk_Rate.webp', 11, true, '3'),

       ('SKILL_RATE', '{
         "en": "Skill Rate",
         "fr": "Taux de compétence"
       }', '{
         "en": "How many skill points your character gains per level.",
         "fr": "Nombre de points de compétence gagnés à chaque niveau."
       }', '/assets/derivedStat/Skill_Rate.webp', 12, true, '(INTELLIGENCE * 2) + 5'),

       ('PARTY_LIMIT', '{
         "en": "Party Limit",
         "fr": "Limite de groupe"
       }', '{
         "en": "The number of followers your character can have.",
         "fr": "Le nombre de compagnons que le joueur peut avoir."
       }', '/assets/derivedStat/Party_Limit.webp', 13, true, 'CHARISMA / 2');
