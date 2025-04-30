ALTER TABLE character
    DROP COLUMN special_id;

DROP TABLE IF EXISTS special;

-- Création de la table special
CREATE TABLE special
(
    id            BIGSERIAL PRIMARY KEY,
    code          VARCHAR(255) NOT NULL UNIQUE,
    names         JSONB        NOT NULL,
    short_names   JSONB        NOT NULL,
    descriptions  JSONB        NOT NULL,
    image_path    VARCHAR(512) NOT NULL,
    display_order INTEGER      NOT NULL    DEFAULT 0,
    visible       BOOLEAN      NOT NULL    DEFAULT true,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table special instance
CREATE TABLE special_instance
(
    id           BIGSERIAL PRIMARY KEY,
    character_id BIGINT  NOT NULL REFERENCES character (id) ON DELETE CASCADE,
    special_id   BIGINT  NOT NULL REFERENCES special (id) ON DELETE CASCADE,
    value        INTEGER NOT NULL,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_character_special UNIQUE (character_id, special_id)
);

INSERT INTO special (code, names, short_names, descriptions, image_path, display_order, visible)
VALUES ('STRENGTH', '{
  "en": "Strength",
  "fr": "Force"
}', '{
  "en": "ST",
  "fr": "FO"
}', '{
  "en": "Raw physical strength. A high Strength is good for physical characters. Modifies: Hit Points, Melee Damage, and Carry Weight.",
  "fr": "Force physique brute. Pour les rôles physiques, il est important de posséder une grande force. Détermine les points d\"impact, les dégâts en Rixe et la capacité de charge (poids transporté)."
}', '/assets/special/Strength.webp', 0, true),
       ('PERCEPTION', '{
         "en": "Perception",
         "fr": "Perception"
       }', '{
         "en": "PE",
         "fr": "PE"
       }',
        '{
          "en": "The ability to see, hear, taste and notice unusual things. A high Perception is important for a sharpshooter. Modifies: Sequence and ranged combat distance modifiers.",
          "fr": "Capacité à voir, entendre, goûter et détecter l\"inhabituel. Il est décisif pour un tireur d\"élite de posséder d\"excellentes facultés de perception. Détermine les modificateurs de Séquence et de la portée de tir."
        }', '/assets/special/Perception.webp', 1, true),

       ('ENDURANCE', '{
         "en": "Endurance",
         "fr": "Endurance"
       }', '{
         "en": "EN",
         "fr": "EN"
       }',
        '{
          "en": "Stamina and physical toughness. A character with a high Endurance will survive where others may not. Modifies: Hit Points, Poison & Radiation Resistance, Healing Rate, and additional hit points per level.",
          "fr": "Résistance physique et vigueur. Meilleures chances de survie. Modifie : points d\"impact, résistance au poison et radiations, vitesse de guérison et points d\"impact supplémentaires par niveau."
        }', '/assets/special/Endurance.webp', 2, true),

       ('CHARISMA', '{
         "en": "Charisma",
         "fr": "Charisme"
       }', '{
         "en": "CH",
         "fr": "CH"
       }',
        '{
          "en": "A combination of appearance and charm. A high Charisma is important for characters that want to influence people with words. Modifies: NPC reactions and barter prices.",
          "fr": "Charme et façon d\"être. Posséder un charisme élevé est très important pour les personnages devant se montrer très persuasifs par leur discours. Détermine les réactions des PNJ et le niveau des prix du troc."
        }', '/assets/special/Charisma.webp', 3, true),

       ('INTELLIGENCE', '{
         "en": "Intelligence",
         "fr": "Intelligence"
       }', '{
         "en": "IN",
         "fr": "IN"
       }',
        '{
          "en": "Knowledge, wisdom and the ability to think quickly. A high Intelligence is important for any character. Modifies: the number of new skill points per level, dialogue options, and many skills.",
          "fr": "Savoir, sagesse et sagacité. Faire preuve d\"une forte intelligence est décisif quel que soit le rôle du personnage. Détermine le nombre de points de compétence par niveau, les options de dialogues et de nombreuses compétences."
        }', '/assets/special/Intelligence.webp', 4, true),

       ('AGILITY', '{
         "en": "Agility",
         "fr": "Agilité"
       }', '{
         "en": "AG",
         "fr": "AG"
       }',
        '{
          "en": "Coordination and the ability to move well. A high Agility is important for any active character. Modifies: Action Points, Armor Class, Sequence, and many skills.",
          "fr": "Coordination des mouvements et faculté de déplacement. L\"agilité est importante pour les personnes actives. Détermine les points d\"action, la Classe d\"Armure, la Séquence et de nombreuses compétences."
        }', '/assets/special/Agility.webp', 5, true),

       ('LUCK', '{
         "en": "Luck",
         "fr": "Chance"
       }', '{
         "en": "LU",
         "fr": "CH"
       }',
        '{
          "en": "Fate. Karma. An extremely high or low Luck will affect the character - somehow. Events and situations will be changed by how lucky (or unlucky) your character is.",
          "fr": "Destin, Karma. Une chance extrêmement élevée ou faible aura une incidence sur votre personnage - d\"une manière ou d\"une autre. Les événements et les situations seront changés par la chance (ou la malchance) de votre personnage."
        }', '/assets/special/Luck.webp', 6, true);
