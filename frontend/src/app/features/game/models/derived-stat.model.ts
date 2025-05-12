import { BaseNamedEntity } from '@features/game/models/common/base-named.model';

export interface DerivedStatInstance {
	id: number;
	value: number;
	active: boolean;
	derivedStatId: number;
}

export interface DerivedStat extends BaseNamedEntity {
}

export enum DerivedStatEnum {
	ACTION_POINTS = 'ACTION_POINTS',
	ARMOR_CLASS = 'ARMOR_CLASS',
	CARRY_WEIGHT = 'CARRY_WEIGHT',
	HIT_POINTS = 'HIT_POINTS',
}
