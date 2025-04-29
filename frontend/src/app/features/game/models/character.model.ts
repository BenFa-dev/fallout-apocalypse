import { BaseModel } from '@features/game/models/base.model';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { ArmorDamage, Inventory } from '@features/game/models/inventory/inventory.model';
import { SkillInstance } from '@features/game/models/skill.model';
import { SpecialInstance } from '@features/game/models/special.model';

export interface Character extends BaseModel {
	id?: number;
	name: string;
	currentX: number;
	currentY: number;
	age: number;
	gender: 'female' | 'male' | 'other';
	inventory: Inventory;
	stats?: CharacterStats;
	currentStats?: CharacterCurrentStats;
	skills: SkillInstance[];
	specials: SpecialInstance[];
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

export interface CharacterSheet {
	isOpen: boolean;
	selectedItem?: BaseNamedEntity
}
