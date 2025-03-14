import { BaseModel } from '@features/game/models/base.model';
import { Tile } from '@features/game/models/tile.model';

export interface Map extends BaseModel {
  name: string,
  width: number,
  height: number,
  tiles: Tile[]
}
