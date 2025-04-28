import { BaseModel } from '@features/game/models/base.model';
import { ArmorDamage, Inventory } from '@features/game/models/inventory/inventory.model';
import { SkillInstance } from '@features/game/models/skill.model';

export interface Character extends BaseModel {
	id?: number;
	name: string;
	currentX: number;
	currentY: number;
	age: number;
	gender: 'female' | 'male' | 'other';
	special: Special;
	inventory: Inventory;
	stats?: CharacterStats;
	currentStats?: CharacterCurrentStats;
	skills: SkillInstance[];
}

export interface CharacterCurrentStats {
	actionPoints: number;
	hitPoints: number;
	level: number;
	experience: number;
	status: {
		poisoned: boolean;
		radiated: boolean;
		eyeDamage: boolean;
		rightArmCrippled: boolean;
		leftArmCrippled: boolean;
		rightLegCrippled: boolean;
		leftLegCrippled: boolean;
	}
}

export interface CharacterStats {
	actionPoints: number;
	armorClass: number;
	damages: ArmorDamage[];
	carryWeight: number;
	criticalChance: number;
	healingRate: number;
	poisonResistance: number;
	radiationResistance: number;
	hitPoints: number;
	meleeDamage: number;
	sequence: number;
	skillPoints: number;
	perkRate: number;
	partyLimit: number;
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
