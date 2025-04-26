import { BaseModel } from '@features/game/models/base.model';
import { ArmorDamage, Inventory } from '@features/game/models/inventory/inventory.model';

export interface Character extends BaseModel {
	id?: number;
	name: string;
	currentX: number;
	currentY: number;
	currentActionPoints: number;
	maxHitPoints: number;
	special: Special;
	hitPoints: number;
	inventory: Inventory;
	stats?: CharacterStats;
}

export interface CharacterStats {
	maxActionPoints: number;
	armorClass: number;
	damages: ArmorDamage[];
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
