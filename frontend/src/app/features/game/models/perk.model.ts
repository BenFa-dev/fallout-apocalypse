import { BaseNamedEntity } from '@features/game/models/common/base-named.model';

export interface PerkInstance {
	id: number;
	value: number;
	active: boolean;
	perkId: number;
}

export interface Perk extends BaseNamedEntity {
}
