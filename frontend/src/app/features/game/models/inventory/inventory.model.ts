// inventory.model.ts

export enum ItemType {
	WEAPON = 'WEAPON',
	ARMOR = 'ARMOR',
	AMMO = 'AMMO'
}

export enum EquippedSlot {
	PRIMARY_WEAPON = 'PRIMARY_WEAPON',
	SECONDARY_WEAPON = 'SECONDARY_WEAPON',
	ARMOR = 'ARMOR',
}

export enum WeaponModeType {
	SINGLE = 'SINGLE',
	AIMED = 'AIMED',
	BURST = 'BURST',
	THROW = 'THROW',
	SWING = 'SWING',
	THRUST = 'THRUST',
	PUNCH = 'PUNCH'
}

export enum WeaponType {
	SMALL_GUN = 'SMALL_GUN',
	BIG_GUN = 'BIG_GUN',
	ENERGY = 'ENERGY',
	MELEE = 'MELEE',
	UNARMED = 'UNARMED',
	THROWING = 'THROWING'
}

export enum DamageType {
	NORMAL = 'NORMAL',
	LASER = 'LASER',
	FIRE = 'FIRE',
	PLASMA = 'PLASMA',
	EXPLOSIVE = 'EXPLOSIVE',
	ELECTRIC = 'ELECTRIC'
}

export const WeaponModeIcons: Readonly<Record<WeaponModeType, string>> = {
	SINGLE: '🔫',
	AIMED: '🎯',
	BURST: '💥',
	THROW: '🎯',
	SWING: '⚔️',
	THRUST: '🗡️',
	PUNCH: '👊'
};

export interface Item {
	id: number;
	type: ItemType;
	names: Record<string, string>;
	descriptions: Record<string, string>;
	weight: number;
	basePrice: number;
	path: string;
}

export interface ItemInstance {
	id: number;
	item: Item;
}

export interface WeaponInstance extends ItemInstance {
	quantity?: number;
	currentAmmoType: Ammo;
	currentAmmoQuantity?: number;
	equippedSlot: EquippedSlot | null;
}

export interface ArmorInstance extends ItemInstance {
	equippedSlot: EquippedSlot | null;
}

export interface AmmoInstance extends ItemInstance {
	quantity: number;
}

export interface Weapon extends Item {
	weaponType: WeaponType;
	requiredStrength: number;
	requiredHands: number;
	capacity?: number;
	damageType: DamageType;
	weaponModes: WeaponMode[];
	compatibleAmmo: Ammo[];
}

export interface WeaponMode {
	id: number;
	modeType: WeaponModeType;
	actionPoints: number;
	minDamage: number;
	maxDamage: number;
	range: number;
	shotsPerBurst: number;
}

export interface Armor extends Item {
	armorClass: number;
	damageThresholdNormal: number;
	damageThresholdLaser: number;
	damageThresholdFire: number;
	damageThresholdPlasma: number;
	damageThresholdExplosive: number;
	damageThresholdElectric: number;
	damageResistanceNormal: number;
	damageResistanceLaser: number;
	damageResistanceFire: number;
	damageResistancePlasma: number;
	damageResistanceExplosive: number;
	damageResistanceElectric: number;
}

export interface Ammo extends Item {
	armorClassModifier: number;
	damageResistanceModifier: number;
	damageModifier: number;
	damageThresholdModifier: number;
}

export interface Inventory {
	id: number;
	characterId: number;
	items: ItemInstance[];
	currentWeight: number;
	maxWeight: number;
}

export interface WeaponDetail {
	instance: WeaponInstance | null;
	item: Weapon | null;
	mode: WeaponMode | null | undefined
}

export interface ArmorDetail {
	instance: ArmorInstance | null;
	item: Armor | null;
}

export interface ItemDetail {
	instance: ItemInstance | null;
	item: Item | null;
}





