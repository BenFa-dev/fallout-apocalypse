export interface BaseNamedEntity {
	id: number;
	code: string;
	names: Record<string, string>;
	shortNames?: Record<string, string>;
	descriptions: Record<string, string>;
	displayOrder: number;
	visible?: boolean;
	imagePath?: string;
}

export interface BaseNamedIntegerInstance {
	id: number;
	value: number;
	active: boolean;
}

export interface BaseNamedBooleanInstance {
	id: number;
	value: boolean;
	active: boolean;
}

export interface BaseNamedTaggedInstance extends BaseNamedIntegerInstance {
	tagged: boolean;
}

