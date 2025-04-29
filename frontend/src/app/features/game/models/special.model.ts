import { BaseNamedEntity } from '@features/game/models/common/base-named.model';

export interface SpecialInstance {
	id: number;
	value: number;
	tagged: boolean;
	specialId: number;
}

export interface Special extends BaseNamedEntity {
	shortNames: Record<string, string>;
}
