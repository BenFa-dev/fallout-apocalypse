import { BaseNamedEntity } from '@features/game/models/common/base-named.model';

export interface Condition extends BaseNamedEntity {
}

export interface ConditionInstance {
	id: number;
	value: boolean;
	active: boolean;
	conditionId: number;
}
