import { Position } from '@features/game/models/position.model';
import * as Phaser from 'phaser';

export interface Tile {
  position: Position;
  type: string;
  sprite?: Phaser.GameObjects.Sprite;
  descriptions?: { [key: string]: string };
}
