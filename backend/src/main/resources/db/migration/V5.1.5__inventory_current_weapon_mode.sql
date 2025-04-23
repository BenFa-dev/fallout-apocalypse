ALTER TABLE weapon_instance
    ADD COLUMN current_weapon_mode_id BIGINT REFERENCES weapon_mode (id);
