export interface TerrainConfiguration {
  name: string;
  descriptions: { [key: string]: string };
  movementCost: number;
  walkable: boolean;
  path: string;
}

export interface TerrainConfigurationMap {
  [key: string]: TerrainConfiguration;
}
