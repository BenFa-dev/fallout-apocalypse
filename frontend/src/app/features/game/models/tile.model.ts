import { BaseModel } from '@features/game/models/base.model';
import { Position } from '@features/game/models/position.model';
import * as Phaser from 'phaser';

export interface Tile extends BaseModel {
  position: Position;
  type: string;
  walkable: boolean;
  movementCost: number;
  revealed: boolean;
  visible: boolean;
  descriptions?: { [key: string]: string };
  sprite?: Phaser.GameObjects.Sprite;
}
