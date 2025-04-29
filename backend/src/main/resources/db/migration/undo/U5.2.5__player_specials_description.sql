DROP TABLE IF EXISTS special_instance;

DROP TABLE IF EXISTS special;

CREATE TABLE special
(
    id           BIGSERIAL PRIMARY KEY,
    strength     INTEGER NOT NULL         DEFAULT 5 CHECK (strength BETWEEN 1 AND 10),
    perception   INTEGER NOT NULL         DEFAULT 5 CHECK (perception BETWEEN 1 AND 10),
    endurance    INTEGER NOT NULL         DEFAULT 5 CHECK (endurance BETWEEN 1 AND 10),
    charisma     INTEGER NOT NULL         DEFAULT 5 CHECK (charisma BETWEEN 1 AND 10),
    intelligence INTEGER NOT NULL         DEFAULT 5 CHECK (intelligence BETWEEN 1 AND 10),
    agility      INTEGER NOT NULL         DEFAULT 5 CHECK (agility BETWEEN 1 AND 10),
    luck         INTEGER NOT NULL         DEFAULT 5 CHECK (luck BETWEEN 1 AND 10),
    created_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

ALTER TABLE character
    ADD COLUMN special_id BIGINT REFERENCES special (id);

DELETE
FROM flyway_schema_history
WHERE version = '5.2.5';