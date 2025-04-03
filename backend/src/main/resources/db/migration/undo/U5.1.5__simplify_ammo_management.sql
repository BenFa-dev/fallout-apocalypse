-- Recréation de la table weapon_instance_ammo
CREATE TABLE weapon_instance_ammo
(
    id                 BIGSERIAL PRIMARY KEY,
    weapon_instance_id BIGINT    NOT NULL REFERENCES weapon_instance (id),
    ammo_id            BIGINT    NOT NULL REFERENCES ammo (id),
    current_ammo       INTEGER   NOT NULL,
    created_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Suppression de la table ammo_instance
DROP TABLE IF EXISTS ammo_instance;

-- Suppression de l'entrée dans flyway_schema_history
DELETE
FROM flyway_schema_history
WHERE version = '5.1.5'; 