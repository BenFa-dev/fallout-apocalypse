ALTER TABLE terrain_configuration RENAME COLUMN svg_path TO path;

UPDATE tile
SET terrain_configuration_id = (SELECT id FROM terrain_configuration WHERE name = 'DESERT')
WHERE terrain_configuration_id IN (SELECT id FROM terrain_configuration WHERE name IN (
                                                                                       'TOXIC_WASTE', 'CRATER', 'BUNKER', 'METRO', 'DEBRIS',
                                                                                       'BARRICADE', 'WRECKAGE', 'DEEP_CRATER', 'COLLAPSED_BUILDING'));

DELETE FROM terrain_configuration WHERE name IN (
                                                 'TOXIC_WASTE', 'CRATER', 'BUNKER', 'METRO', 'DEBRIS',
                                                 'BARRICADE', 'WRECKAGE', 'DEEP_CRATER', 'COLLAPSED_BUILDING');

UPDATE terrain_configuration
SET path = REPLACE(path, '.svg', '.png')
WHERE path LIKE '%.svg';

