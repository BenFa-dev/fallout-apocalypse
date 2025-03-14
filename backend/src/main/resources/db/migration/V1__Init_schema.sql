-- Create special table
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

-- Configuration des types de terrain
CREATE TABLE terrain_configuration
(
    id            BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255)  NOT NULL UNIQUE,
    descriptions  JSONB         NOT NULL,
    movement_cost INT           NOT NULL,
    walkable      BOOLEAN       NOT NULL DEFAULT true,
    svg_path      VARCHAR(1024) NOT NULL,
    CONSTRAINT movement_cost_positive CHECK (movement_cost > 0)
);

-- Create map table
CREATE TABLE map
(
    id         BIGSERIAL PRIMARY KEY,
    name       VARCHAR(255) NOT NULL,
    width      INTEGER      NOT NULL    DEFAULT 10,
    height     INTEGER      NOT NULL    DEFAULT 10,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);

-- Create tiles table
CREATE TABLE tile
(
    id                       BIGSERIAL PRIMARY KEY,
    map_id                   BIGINT  NOT NULL REFERENCES map (id),
    x                        INTEGER NOT NULL,
    y                        INTEGER NOT NULL,
    terrain_configuration_id BIGINT  NOT NULL REFERENCES terrain_configuration (id),
    created_at               TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at               TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT unique_tile_position UNIQUE (map_id, x, y)
);

-- Create character table
CREATE TABLE character
(
    id                    BIGSERIAL PRIMARY KEY,
    name                  VARCHAR(255) NOT NULL,
    user_id               VARCHAR(255) NOT NULL UNIQUE,
    special_id            BIGINT       NOT NULL REFERENCES special (id),
    current_map_id        BIGINT REFERENCES map (id),
    current_x             INTEGER      NOT NULL    DEFAULT 0,
    current_y             INTEGER      NOT NULL    DEFAULT 0,
    current_action_points INTEGER      NOT NULL    DEFAULT 0,
    max_action_points     INTEGER      NOT NULL    DEFAULT 0,
    created_at            TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at            TIMESTAMP WITH TIME ZONE DEFAULT CURRENT_TIMESTAMP
);