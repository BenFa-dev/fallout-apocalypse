import { BaseModel } from '@features/game/models/base.model';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { ConditionInstance } from '@features/game/models/condition.model';
import { DerivedStatInstance } from '@features/game/models/derived-stat.model';
import { Inventory } from '@features/game/models/inventory/inventory.model';
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
	currentStats?: CharacterCurrentStats;
	skills: SkillInstance[];
	perks: PerkInstance[];
	specials: SpecialInstance[];
	derivedStats: DerivedStatInstance[];
	conditions: ConditionInstance[];
}

export interface CharacterCurrentStats {
	actionPoints: number;
	hitPoints: number;
	level: number;
	experience: number;
}

export interface CharacterSheet {
	isOpen: boolean;
	selectedItem?: BaseNamedEntity
}
