import { BaseModel } from '@features/game/models/base.model';
import {
	BaseNamedBooleanInstance,
	BaseNamedEntity,
	BaseNamedIntegerInstance,
	BaseNamedTaggedInstance
} from '@features/game/models/common/base-named.model';
import { Inventory } from '@features/game/models/inventory/inventory.model';

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
	skills: BaseNamedTaggedInstance[];
	perks: BaseNamedTaggedInstance[];
	specials: BaseNamedIntegerInstance[];
	derivedStats: BaseNamedIntegerInstance[];
	conditions: BaseNamedBooleanInstance[];
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
