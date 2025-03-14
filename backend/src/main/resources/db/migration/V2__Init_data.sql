-- Insertion des types de terrain par défaut
INSERT INTO terrain_configuration (name, descriptions, movement_cost, walkable, svg_path)
VALUES
-- Terrains de base
('DESERT', '{"fr": "Terrain désertique aride et sablonneux", "en": "Arid and sandy desert terrain"}', 1, true,
 '/assets/textures/terrains/desert.svg'),

('WASTELAND', '{"fr": "Terres désolées et stériles", "en": "Desolate and barren wasteland"}', 1, true,
 '/assets/textures/terrains/wasteland.svg'),

('CITY_RUINS', '{"fr": "Ruines urbaines parsemées de débris", "en": "Urban ruins scattered with debris"}', 2, true,
 '/assets/textures/terrains/city-ruins.svg'),

('MOUNTAIN', '{"fr": "Terrain montagneux difficile d''accès", "en": "Difficult to access mountainous terrain"}', 3,
 true,
 '/assets/textures/terrains/mountain.svg'),

-- Zones dangereuses
('RADIATED_ZONE', '{"fr": "Zone hautement radioactive", "en": "Highly radioactive zone"}', 2, true,
 '/assets/textures/terrains/radiated-zone.svg'),

('TOXIC_WASTE', '{"fr": "Mare de déchets toxiques", "en": "Toxic waste pool"}', 3, true,
 '/assets/textures/terrains/toxic-waste.svg'),

('CRATER', '{"fr": "Cratère d''explosion", "en": "Explosion crater"}', 2, true,
 '/assets/textures/terrains/crater.svg'),

-- Structures
('HIGHWAY', '{"fr": "Ancienne autoroute", "en": "Old highway"}', 1, true,
 '/assets/textures/terrains/highway.svg'),

('BUNKER', '{"fr": "Bunker militaire", "en": "Military bunker"}', 1, true,
 '/assets/textures/terrains/bunker.svg'),

('METRO', '{"fr": "Réseau de métro abandonné", "en": "Abandoned metro network"}', 2, true,
 '/assets/textures/terrains/metro.svg'),

-- Obstacles
('DEBRIS', '{"fr": "Amas de débris", "en": "Pile of debris"}', 2, true,
 '/assets/textures/terrains/debris.svg'),

('BARRICADE', '{"fr": "Barricade de fortune", "en": "Makeshift barricade"}', 2, true,
 '/assets/textures/terrains/barricade.svg'),

('WRECKAGE', '{"fr": "Carcasses de véhicules", "en": "Vehicle wreckage"}', 2, true,
 '/assets/textures/terrains/wreckage.svg'),

-- Zones inaccessibles
('DEEP_CRATER', '{"fr": "Cratère profond infranchissable", "en": "Impassable deep crater"}', 99, false,
 '/assets/textures/terrains/deep-crater.svg'),

('COLLAPSED_BUILDING', '{"fr": "Bâtiment effondré", "en": "Collapsed building"}', 99, false,
 '/assets/textures/terrains/collapsed-building.svg');