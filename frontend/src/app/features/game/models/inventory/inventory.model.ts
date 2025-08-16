// inventory.model.ts

import { BaseNamedEntity } from '@features/game/models/common/base-named.model';

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

export const WeaponModeIcons: Readonly<Record<WeaponModeType, string>> = {
	SINGLE: '🔫',
	AIMED: '🎯',
	BURST: '💥',
	THROW: '🎯',
	SWING: '⚔️',
	THRUST: '🗡️',
	PUNCH: '👊'
};

export interface Item extends BaseNamedEntity {
	type: ItemType;
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
	currentWeaponMode: WeaponMode;
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
	damageType: BaseNamedEntity;
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
	damages: ArmorDamage[];
}

export interface ArmorDamage {
	id: number;
	threshold: number;
	resistance: number;
	damageType: BaseNamedEntity;
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
}

export interface DragItem {
	slot: EquippedSlot | 'inventory-list' | null,
	itemInstance: ItemInstance | null,
}

export type ItemInstanceKeys = keyof ItemInstance | keyof WeaponInstance | keyof ArmorInstance | keyof AmmoInstance;


