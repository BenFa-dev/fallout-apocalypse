-- Création de la table skill
CREATE TABLE skill
(
    id            BIGSERIAL PRIMARY KEY,
    code          VARCHAR(255) NOT NULL UNIQUE,
    names         JSONB        NOT NULL,
    descriptions  JSONB        NOT NULL,
    display_order INTEGER      NOT NULL    DEFAULT 0,
    visible       BOOLEAN      NOT NULL    DEFAULT true,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table skill_instance
CREATE TABLE skill_instance
(
    id           BIGSERIAL PRIMARY KEY,
    character_id BIGINT  NOT NULL REFERENCES character (id) ON DELETE CASCADE,
    skill_id     BIGINT  NOT NULL REFERENCES skill (id) ON DELETE CASCADE,
    value        INTEGER NOT NULL,
    is_tagged    BOOLEAN NOT NULL         DEFAULT false,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_character_skill UNIQUE (character_id, skill_id)
);


-- Insertion des compétences de combat
INSERT INTO skill (code, names, descriptions, display_order, visible)
VALUES
-- Armes légères
('SMALL_GUNS', '{
  "en": "Small Guns",
  "fr": "Armes légères"
}',
 '{
   "en": "The use, care and general knowledge of small firearms. Pistols, SMGs and rifles.",
   "fr": "Maniement, entretien et connaissance générale des petites armes à feu. Pistolets, mitraillettes et carabines."
 }',
 0, true),

-- Armes lourdes
('BIG_GUNS', '{
  "en": "Big Guns",
  "fr": "Armes lourdes"
}',
 '{
   "en": "The operation and maintenance of really big guns. Miniguns, Rocket Launchers, Flamethrowers and such.",
   "fr": "Fonctionnement et entretien de l\"armement lourd. Mini-canons, lance-roquettes, lance-flammes et autres."
 }',
 1, true),

-- Armes à énergie
('ENERGY_WEAPONS', '{
  "en": "Energy Weapons",
  "fr": "Armes à énergie"
}',
 '{
   "en": "The care and feeding of energy-based weapons. How to arm and operate weapons that use laser or plasma technology.",
   "fr": "Entretien et chargement des armes à énergie. Comment armer et manier des armes utilisant des techniques au laser ou au plasma."
 }',
 2, true),

-- Corps à corps
('UNARMED', '{
  "en": "Unarmed",
  "fr": "Corps à corps"
}',
 '{
   "en": "A combination of martial arts, boxing and other hand-to-hand martial arts. Combat with your hands and feet.",
   "fr": "Divers arts martiaux, combats de boxe et corps à corps. Coups de pied et coups de poings."
 }',
 3, true),

-- Armes blanches
('MELEE_WEAPONS', '{
  "en": "Melee Weapons",
  "fr": "Armes blanches"
}',
 '{
   "en": "Using non-ranged weapons in hand-to-hand, or melee, combat. Knives, sledgehammers, spears, clubs and so on.",
   "fr": "Capacité à combattre à l\"arme blanche au cours de corps à corps ou de rixes. Couteaux, massues, lances, matraques, etc."
 }',
 4, true),

-- Projectiles
('THROWING', '{
  "en": "Throwing",
  "fr": "Projectiles"
}',
 '{
   "en": "The skill of muscle-propelled ranged weapons. Throwing knives, spears and grenades.",
   "fr": "Capacité à lancer des armes à la main, telles que des couteaux, des lances et des grenades."
 }',
 5, true),

-- Insertion des compétences actives
-- Secourisme
('FIRST_AID', '{
  "en": "First Aid",
  "fr": "Secourisme"
}',
 '{
   "en": "General healing skill. Used to heal small cuts, abrasions and other minor ills.",
   "fr": "Capacité à pratiquer les premiers soins. Traitement des coupures légères, écorchures et autres maux bénins."
 }',
 6, true),

-- Médecin
('DOCTOR', '{
  "en": "Doctor",
  "fr": "Médecin"
}',
 '{
   "en": "The healing of major wounds and crippled limbs.",
   "fr": "Capacité à traiter les lésions importantes et à soigner les membres estropiés."
 }',
 7, true),

-- Esquive
('SNEAK', '{
  "en": "Sneak",
  "fr": "Esquive"
}',
 '{
   "en": "Quiet movement, and the ability to remain unnoticed.",
   "fr": "Mouvement furtif permettant de passer inaperçu."
 }',
 8, true),

-- Passe-partout
('LOCKPICK', '{
  "en": "Lockpick",
  "fr": "Passe-partout"
}',
 '{
   "en": "The skill of opening locks without the proper key.",
   "fr": "Capacité à ouvrir les serrures sans la bonne clé."
 }',
 9, true),

-- Voleur
('STEAL', '{
  "en": "Steal",
  "fr": "Voleur"
}',
 '{
   "en": "The ability to make the things of others your own.",
   "fr": "Faculté de s\"approprier ce qui appartient à autrui."
 }',
 10, true),

-- Pièges
('TRAPS', '{
  "en": "Traps",
  "fr": "Pièges"
}',
 '{
   "en": "The finding and removal of traps. Also the setting of explosives.",
   "fr": "Capacité à repérer et retirer les pièges posés. Permet également de poser des explosifs."
 }',
 11, true),

-- Sciences
('SCIENCE', '{
  "en": "Science",
  "fr": "Sciences"
}',
 '{
   "en": "Covers a variety of hi-technology skills, such as computers, biology, physics and geology.",
   "fr": "Recouvre diverses compétences scientifiques, notamment en informatique, biologie, physique et géologie."
 }',
 12, true),

-- Réparation
('REPAIR', '{
  "en": "Repair",
  "fr": "Réparation"
}',
 '{
   "en": "The practical application of the Science skill. The fixing of broken equipment, machinery and electronics.",
   "fr": "Mise en application de la compétence Science. Réparation des équipements, mécanismes et appareils électroniques endommagés."
 }',
 13, true),

-- Insertion des compétences passives
-- Discours
('SPEECH', '{
  "en": "Speech",
  "fr": "Discours"
}',
 '{
   "en": "The ability to communicate in a practical and efficient manner.",
   "fr": "Capacité à communiquer de manière pratique et efficace."
 }',
 14, true),

-- Troc
('BARTER', '{
  "en": "Barter",
  "fr": "Troc"
}',
 '{
   "en": "Trading and trade-related tasks.",
   "fr": "Sens du commerce. Capacité à tout marchander; à la vente comme à l\"achat."
 }',
 15, true),

-- Jeu
('GAMBLING', '{
  "en": "Gambling",
  "fr": "Jeu"
}',
 '{
   "en": "The knowledge and practical skills related to wagering.",
   "fr": "L\"art et la manière de parier. Talent aux jeux de cartes, de dés et autres."
 }',
 16, true),

-- Vie en plein air
('OUTDOORSMAN', '{
  "en": "Outdoorsman",
  "fr": "Vie en plein air"
}',
 '{
   "en": "Practical knowledge of the outdoors, and the ability to live off the land.",
   "fr": "Connaissance pratique de la vie en plein air et capacité à survivre en milieu hostile."
 }',
 17, true);

