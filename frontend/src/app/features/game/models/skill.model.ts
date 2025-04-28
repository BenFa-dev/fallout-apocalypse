export interface SkillInstance {
	id: number;
	value: number;
	tagged: boolean;
	skillId: number;
}

export interface Skill {
	id: number;
	code: string;
	names: Record<string, string>;
	descriptions: Record<string, string>;
	displayOrder: number;
	visible: boolean;
}
