-- Types d'objets disponibles dans le jeu
CREATE TYPE item_type AS ENUM ('WEAPON', 'ARMOR', 'AMMO');

-- Types d'armes (catégories)
CREATE TYPE weapon_type AS ENUM (
    'UNARMED', -- Combat à mains nues
    'MELEE', -- Armes de mêlée
    'SMALL_GUN', -- Armes légères
    'BIG_GUN', -- Armes lourdes
    'ENERGY', -- Armes à énergie
    'THROWING' -- Armes de jet
    );

-- Modes d'utilisation des armes
CREATE TYPE weapon_mode_type AS ENUM (
    'SINGLE', -- Mode standard pour les armes à feu
    'AIMED', -- Mode visé
    'BURST', -- Mode rafale
    'THROW', -- Pour les armes de jet
    'SWING', -- Pour les armes de mêlée
    'THRUST', -- Pour les armes de mêlée (coup d'estoc)
    'PUNCH' -- Pour les armes de combat à mains nues
    );

-- Types de dégâts
CREATE TYPE damage_type AS ENUM (
    'NORMAL',
    'ELECTRICAL',
    'EXPLOSION',
    'PLASMA',
    'LASER',
    'FIRE',
    'EMP'
    );

-- Slot occupé
CREATE TYPE equipped_slot AS ENUM (
    'PRIMARY_WEAPON',
    'SECONDARY_WEAPON',
    'ARMOR'
    );

-- Table de base pour tous les objets du jeu
CREATE TABLE item
(
    id           BIGSERIAL PRIMARY KEY,
    type         item_type        NOT NULL, -- Type d'objet (arme, armure, munition)
    names        JSONB            NOT NULL, -- Noms traduits {"en": "Name", "fr": "Nom"}
    descriptions JSONB            NOT NULL, -- Descriptions traduites {"en": "Desc", "fr": "Desc"}
    weight       DOUBLE PRECISION NOT NULL, -- Poids en livres (lbs)
    base_price   INTEGER          NOT NULL, -- Prix de base en caps
    path         VARCHAR(255)     NOT NULL, -- Chemin vers l'image de l'objet
    created_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Table spécifique pour les armes
CREATE TABLE weapon
(
    id                BIGINT PRIMARY KEY REFERENCES item (id),
    weapon_type       weapon_type NOT NULL, -- Catégorie d'arme
    required_strength INTEGER     NOT NULL, -- Force minimale requise pour utiliser l'arme
    required_hands    INTEGER     NOT NULL, -- Nombre de mains nécessaires (1 ou 2)
    capacity          INTEGER,              -- Capacité du chargeur (NULL pour les armes sans munition)
    damage_type       damage_type NOT NULL DEFAULT 'NORMAL'
);

-- Table pour les armures
CREATE TABLE armor
(
    id                          BIGINT PRIMARY KEY REFERENCES item (id),
    armor_class                 INTEGER NOT NULL, -- Classe d'armure (AC)
    damage_threshold_normal     INTEGER NOT NULL, -- Réduction fixe des dégâts normaux
    damage_threshold_laser      INTEGER NOT NULL, -- Réduction fixe des dégâts laser
    damage_threshold_fire       INTEGER NOT NULL, -- Réduction fixe des dégâts feu
    damage_threshold_plasma     INTEGER NOT NULL, -- Réduction fixe des dégâts plasma
    damage_threshold_explosive  INTEGER NOT NULL, -- Réduction fixe des dégâts explosifs
    damage_threshold_electric   INTEGER NOT NULL, -- Réduction fixe des dégâts électriques
    damage_resistance_normal    INTEGER NOT NULL, -- Pourcentage de réduction des dégâts normaux
    damage_resistance_laser     INTEGER NOT NULL, -- Pourcentage de réduction des dégâts laser
    damage_resistance_fire      INTEGER NOT NULL, -- Pourcentage de réduction des dégâts feu
    damage_resistance_plasma    INTEGER NOT NULL, -- Pourcentage de réduction des dégâts plasma
    damage_resistance_explosive INTEGER NOT NULL, -- Pourcentage de réduction des dégâts explosifs
    damage_resistance_electric  INTEGER NOT NULL  -- Pourcentage de réduction des dégâts électriques
);

-- Table pour les munitions
CREATE TABLE ammo
(
    id                         BIGINT PRIMARY KEY REFERENCES item (id),
    armor_class_modifier       INTEGER NOT NULL, -- Modificateur de classe d'armure (%)
    damage_resistance_modifier INTEGER NOT NULL, -- Modificateur de résistance aux dégâts (%)
    damage_modifier            INTEGER NOT NULL, -- Multiplicateur de dégâts (ex: 2 = double dégâts)
    damage_threshold_modifier  INTEGER NOT NULL  -- Multiplicateur de pénétration d'armure
);

-- Table de liaison entre armes et munitions compatibles
CREATE TABLE weapon_compatible_ammo
(
    weapon_id  BIGINT REFERENCES weapon (id),
    ammo_id    BIGINT REFERENCES ammo (id),
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (weapon_id, ammo_id)
);

-- Table pour les différents modes d'utilisation des armes
CREATE TABLE weapon_mode
(
    id              BIGSERIAL PRIMARY KEY,
    weapon_id       BIGINT REFERENCES weapon (id),
    mode_type       weapon_mode_type NOT NULL, -- Type de mode (tir simple, visé, etc.)
    min_damage      INTEGER          NOT NULL, -- Dégâts minimum
    max_damage      INTEGER          NOT NULL, -- Dégâts maximum
    range           INTEGER          NOT NULL, -- Portée en cases
    action_points   INTEGER          NOT NULL, -- Points d'action nécessaires
    shots_per_burst INTEGER,                   -- Nombre de tirs pour le mode BURST
    created_at      TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at      TIMESTAMP        NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table item_instance (table de base pour les instances)
CREATE TABLE item_instance
(
    id           BIGSERIAL PRIMARY KEY,
    item_id      BIGINT      NOT NULL REFERENCES item (id),
    inventory_id BIGINT,
    item_type    VARCHAR(20) NOT NULL,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Création de la table weapon_instance
CREATE TABLE weapon_instance
(
    id                    BIGINT PRIMARY KEY REFERENCES item_instance (id),
    current_ammo_type_id  BIGINT REFERENCES ammo (id),
    current_ammo_quantity INTEGER,
    equipped_slot         equipped_slot
);

-- Création de la table armor_instance
CREATE TABLE armor_instance
(
    id            BIGINT PRIMARY KEY REFERENCES item_instance (id),
    equipped_slot equipped_slot
);

-- Création de la table ammo_instance pour gérer les munitions dans l'inventaire
CREATE TABLE ammo_instance
(
    id       BIGINT PRIMARY KEY REFERENCES item_instance (id),
    quantity INTEGER NOT NULL DEFAULT 0
);

-- Création de la table inventory
CREATE TABLE inventory
(
    id           BIGSERIAL PRIMARY KEY,
    character_id BIGINT    NOT NULL REFERENCES character (id),
    created_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Ajout de la contrainte de clé étrangère pour inventory_id dans item_instance
ALTER TABLE item_instance
    ADD CONSTRAINT fk_item_instance_inventory
        FOREIGN KEY (inventory_id) REFERENCES inventory (id) ON DELETE SET NULL;