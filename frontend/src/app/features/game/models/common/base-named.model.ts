export interface BaseNamedEntity {
	id: number;
	code: string;
	names: Record<string, string>;
	descriptions: Record<string, string>;
	displayOrder: number;
	visible?: boolean;
	imagePath?: string;
}
