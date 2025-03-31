CREATE TABLE character_tile_discovery
(
    id           BIGSERIAL PRIMARY KEY,
    character_id BIGINT NOT NULL REFERENCES character (id) ON DELETE CASCADE,
    tile_id      BIGINT NOT NULL REFERENCES tile (id) ON DELETE CASCADE,
    CONSTRAINT unique_character_tile UNIQUE (character_id, tile_id)
);
