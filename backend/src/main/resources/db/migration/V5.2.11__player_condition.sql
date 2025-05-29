-- Create 'condition' table
CREATE TABLE condition
(
    id            BIGSERIAL PRIMARY KEY,
    code          VARCHAR(255) NOT NULL UNIQUE,
    names         JSONB        NOT NULL,
    descriptions  JSONB        NOT NULL,
    display_order INTEGER      NOT NULL    DEFAULT 0,
    image_path    VARCHAR(512) NOT NULL,
    visible       BOOLEAN      NOT NULL    DEFAULT true,
    created_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at    TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE condition_instance
(
    id           BIGSERIAL PRIMARY KEY,
    character_id BIGINT  NOT NULL REFERENCES character (id) ON DELETE CASCADE,
    condition_id BIGINT  NOT NULL REFERENCES condition (id) ON DELETE CASCADE,
    value        BOOLEAN NOT NULL         DEFAULT false,
    is_active    BOOLEAN NOT NULL         DEFAULT false,
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_character_condition UNIQUE (character_id, condition_id)
);

ALTER TABLE character
    DROP COLUMN is_poisoned,
    DROP COLUMN is_radiated,
    DROP COLUMN has_eye_damage,
    DROP COLUMN is_right_arm_crippled,
    DROP COLUMN is_left_arm_crippled,
    DROP COLUMN is_right_leg_crippled,
    DROP COLUMN is_left_leg_crippled;

INSERT INTO condition (code, names, descriptions, image_path, display_order, visible)
VALUES
-- Empoisonnement / Poisoned
('POISONED',
 '{
   "en": "Poisoned",
   "fr": "Empoisonnement"
 }',
 '{
   "en": "Your character has been poisoned. Poison will do damage over a period of time, until cured or it passes from your system.",
   "fr": "Ton personnage a été empoisonné. Le poison agira un certain temps, jusqu\"à ce que le mal soit éradiqué ou jusqu\"à ce qu\"il soit soigné. L\"Endurence augmente ta résistance au poison."
 }',
 '/assets/condition/Poisoned.webp', 0, true),

-- Irradiation / Radiated
('RADIATED',
 '{
   "en": "Radiated",
   "fr": "Irradiation"
 }',
 '{
   "en": "Your character is suffering from a significant amount of Radiation poisoning. The more radiation damage, the more deadly the effect.",
   "fr": "Ton personnage a été sérieusement irradié. Plus les radiations sont importantes, plus leurs effets sont mortels. Monter à 1000 ou plus signifie généralement une mort certaine."
 }',
 '/assets/condition/Radiated.webp', 1, true),

-- Eye Damage / Blessure à l'œil
('EYE_DAMAGE',
 '{
   "en": "Eye Damage",
   "fr": "Blessure à l\"œil"
 }',
 '{
   "en": "This means your character has been seriously hit in one or both of your eyes. This affects your Perception.",
   "fr": "Cela signifie que ton personnage a été sérieusement touché à un œil ou aux deux yeux. Ta perception en sera affectée."
 }',
 '/assets/condition/Eye_Damage.webp', 2, true),

-- Crippled Right Arm / Bras dr. estropié
('CRIPPLED_RIGHT_ARM',
 '{
   "en": "Crippled Right Arm",
   "fr": "Bras dr. estropié"
 }',
 '{
   "en": "The right arm has been severely hurt, and cannot function well. If one arm has been crippled, you cannot use two-handed weapons. If both arms have been crippled, you cannot attack with weapons.",
   "fr": "Le bras droit du personnage a été gravement touché et estropié. Lorsqu\"un bras est estropié, tu ne peux plus utiliser d\"armes à deux mains. Lorsque les deux bras sont estropiés, tu ne peux plus utiliser d\"arme pour attaquer."
 }',
 '/assets/condition/Crippled_Right_Arm.webp', 3, true),

-- Crippled Left Arm / Bras g. estropié
('CRIPPLED_LEFT_ARM',
 '{
   "en": "Crippled Left Arm",
   "fr": "Bras g. estropié"
 }',
 '{
   "en": "Your character\"s left arm has been severely hurt, and cannot function well. If one arm has been crippled, you cannot use two-handed weapons. If both arms have been crippled, you cannot attack with weapons.",
   "fr": "Le bras gauche du personnage a été gravement touché et estropié. Lorsqu\"un bras est estropié, tu ne peux plus utiliser d\"armes à deux mains. Lorsque les deux bras sont estropiés, tu ne peux plus utiliser d\"arme pour attaquer."
 }',
 '/assets/condition/Crippled_Left_Arm.webp', 4, true),

-- Crippled Right Leg / Jambe dr. estropiée
('CRIPPLED_RIGHT_LEG',
 '{
   "en": "Crippled Right Leg",
   "fr": "Jambe dr. estropiée"
 }',
 '{
   "en": "Your character has a crippled right leg.",
   "fr": "La jambe droite de ton personnage est estropiée. Cela affecte ta vitesse en combat, et tu ne peux pas courir."
 }',
 '/assets/condition/Crippled_Right_Leg.webp', 5, true),

-- Crippled Left Leg / Jambe g. estropiée
('CRIPPLED_LEFT_LEG',
 '{
   "en": "Crippled Left Leg",
   "fr": "Jambe g. estropiée"
 }',
 '{
   "en": "Your character has a crippled left leg.",
   "fr": "La jambe gauche de ton personnage est estropiée. Cela affecte ta vitesse en combat, et tu ne peux pas courir."
 }',
 '/assets/condition/Crippled_Left_Leg.webp', 6, true);