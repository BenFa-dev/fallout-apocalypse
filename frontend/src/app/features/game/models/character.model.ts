import { BaseModel } from '@features/game/models/base.model';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { DerivedStatInstance } from '@features/game/models/derived-stat.model';
import { ArmorDamage, Inventory } from '@features/game/models/inventory/inventory.model';
import { PerkInstance } from '@features/game/models/perk.model';
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
}

export interface CharacterFull extends Character {
	stats?: CharacterStats;
	currentStats?: CharacterCurrentStats;
	status?: CharacterStatus;
	skills: SkillInstance[];
	perks: PerkInstance[];
	specials: SpecialInstance[];
	derivedStats: DerivedStatInstance[];
}

export interface CharacterCurrentStats {
	actionPoints: number;
	hitPoints: number;
	level: number;
	experience: number;
}

export interface CharacterStatus {
	poisoned: boolean;
	radiated: boolean;
	eyeDamage: boolean;
	rightArmCrippled: boolean;
	leftArmCrippled: boolean;
	rightLegCrippled: boolean;
	leftLegCrippled: boolean;
}

export interface CharacterStats {
	damages: ArmorDamage[];
}

export interface CharacterSheet {
	isOpen: boolean;
	selectedItem?: BaseNamedEntity
}
