import { BaseNamedEntity } from '@features/game/models/common/base-named.model';

export interface SkillInstance {
	id: number;
	value: number;
	tagged: boolean;
	skillId: number;
}

export interface Skill extends BaseNamedEntity {
}
