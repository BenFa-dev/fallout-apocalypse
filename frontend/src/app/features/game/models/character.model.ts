import { BaseModel } from '@features/game/models/base.model';

export interface Character extends BaseModel {
  id?: number;
  name: string;
  currentX: number;
  currentY: number;
  currentActionPoints: number;
  maxActionPoints: number;
  maxHitPoints: number;
  special: Special;
  hitPoints: number;
  armorClass: number;
}

export interface Special {
  strength: number;
  perception: number;
  endurance: number;
  charisma: number;
  intelligence: number;
  agility: number;
  luck: number;
}
