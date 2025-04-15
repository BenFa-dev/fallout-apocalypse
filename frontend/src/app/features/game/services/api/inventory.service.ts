import { Injectable } from '@angular/core';
import {
	Ammo,
	AmmoInstance,
	ArmorInstance,
	DamageType,
	EquippedSlot,
	Inventory,
	ItemType,
	Weapon,
	WeaponInstance,
	WeaponMode,
	WeaponModeType,
	WeaponType
} from '@features/game/models/inventory/inventory.model';
import { delay, Observable, of } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class InventoryService {
	getInventory(): Observable<Inventory> {
		return of({
			id: 1,
			characterId: 42,
			currentWeight: 23.5,
			maxWeight: 75,
			items: [
				{
					id: 1,
					item: {
						id: 1,
						type: ItemType.WEAPON,
						names: { en: '10mm Pistol', fr: 'Pistolet 10mm' },
						descriptions: { en: '', fr: '' },
						weight: 3,
						basePrice: 250,
						path: 'assets/items/10mm_pistol.webp',
						weaponType: WeaponType.SMALL_GUN,
						requiredStrength: 2,
						requiredHands: 1,
						capacity: 8,
						damageType: DamageType.NORMAL,
						weaponModes: [
							{
								id: 2,
								modeType: WeaponModeType.AIMED,
								actionPoints: 6,
								minDamage: 5,
								maxDamage: 15,
								range: 5
							} as WeaponMode,
							{
								id: 1,
								modeType: WeaponModeType.SINGLE,
								actionPoints: 5,
								minDamage: 5,
								maxDamage: 15,
								range: 5
							} as WeaponMode
						],
						compatibleAmmo: [{
							id: 80,
							type: ItemType.AMMO,
							names: { en: '10mm JHP', fr: '10mm JHP' },
							descriptions: { en: '', fr: '' },
							weight: 0.1,
							basePrice: 5,
							path: 'assets/items/ammo_10mm.webp',
							armorClassModifier: 0,
							damageResistanceModifier: 0,
							damageModifier: 5,
							damageThresholdModifier: 0
						} as Ammo]
					} as Weapon,
					quantity: 1,
					currentAmmoType: {
						id: 10,
						type: ItemType.AMMO,
						names: { en: '10mm JHP', fr: '10mm JHP' },
						descriptions: { en: '', fr: '' },
						weight: 0.1,
						basePrice: 5,
						path: 'assets/items/ammo_10mm.webp',
						armorClassModifier: 0,
						damageResistanceModifier: 0,
						damageModifier: 5,
						damageThresholdModifier: 0
					},
					currentAmmoQuantity: 8,
					equippedSlot: EquippedSlot.PRIMARY_WEAPON
				} as WeaponInstance,
				{
					id: 4,
					item: {
						id: 8,
						type: ItemType.WEAPON,
						damageType: DamageType.NORMAL,
						weaponType: WeaponType.SMALL_GUN,
						requiredStrength: 4,
						requiredHands: 1,
						names: { en: '10mm SMG', fr: 'SMG 10mm' },
						descriptions: { en: '', fr: '' },
						weight: 3,
						basePrice: 250,
						path: 'assets/items/10mm_SMG.webp',
						capacity: 30,
						weaponModes: [
							{
								id: 4,
								modeType: WeaponModeType.BURST,
								actionPoints: 8,
								minDamage: 5,
								maxDamage: 15,
								range: 5,
								shotsPerBurst: 8
							},
							{
								id: 3,
								modeType: WeaponModeType.SINGLE,
								actionPoints: 5,
								minDamage: 5,
								maxDamage: 15,
								range: 5
							}
						]
					},
					quantity: 1,
					currentAmmoType: {
						id: 80,
						type: ItemType.AMMO,
						names: { en: '10mm JHP', fr: '10mm JHP' },
						descriptions: { en: '', fr: '' },
						weight: 0.1,
						basePrice: 5,
						path: 'assets/items/ammo_10mm.webp',
						armorClassModifier: 0,
						damageResistanceModifier: 0,
						damageModifier: 5,
						damageThresholdModifier: 0
					},
					currentAmmoQuantity: 15,
					equippedSlot: null
				} as WeaponInstance,
				{
					id: 2,
					item: {
						id: 2,
						type: ItemType.ARMOR,
						names: { en: 'Leather Armor', fr: 'Armure en cuir' },
						descriptions: { en: '', fr: '' },
						weight: 8,
						basePrice: 800,
						path: 'assets/items/leather_armor.webp',
						armorClass: 15,
						damageThresholdNormal: 2,
						damageThresholdLaser: 0,
						damageThresholdFire: 0,
						damageThresholdPlasma: 0,
						damageThresholdExplosive: 0,
						damageThresholdElectric: 0,
						damageResistanceNormal: 25,
						damageResistanceLaser: 20,
						damageResistanceFire: 20,
						damageResistancePlasma: 20,
						damageResistanceExplosive: 30,
						damageResistanceElectric: 0
					},
					equippedSlot: EquippedSlot.ARMOR
				} as ArmorInstance,
				{
					id: 999,
					item: {
						id: 88,
						type: ItemType.ARMOR,
						names: { en: 'Metal Armor', fr: 'Armure de métal' },
						descriptions: { en: '', fr: '' },
						weight: 35,
						basePrice: 1100,
						path: 'assets/items/metal_armor.webp',
						armorClass: 10,
						damageThresholdNormal: 4,
						damageThresholdLaser: 6,
						damageThresholdFire: 4,
						damageThresholdPlasma: 4,
						damageThresholdExplosive: 4,
						damageThresholdElectric: 0,
						damageResistanceNormal: 30,
						damageResistanceLaser: 75,
						damageResistanceFire: 10,
						damageResistancePlasma: 20,
						damageResistanceExplosive: 25,
						damageResistanceElectric: 0
					},
					equippedSlot: null
				} as ArmorInstance,
				{
					id: 3,
					item: {
						id: 3,
						type: ItemType.AMMO,
						names: { en: '10mm JHP', fr: '10mm JHP' },
						descriptions: {
							en: 'The 10mm is the most common ammo found in the wasteland. The JHP is intended to shoot low-armored targets, while AP gives more power to cut through armor but has lower overall damage.',
							fr: ''
						},
						weight: 0.1,
						basePrice: 5,
						path: 'assets/items/ammo_10mm.webp',
						armorClassModifier: 0,
						damageResistanceModifier: 25,
						damageModifier: 2,
						damageThresholdModifier: 1
					},
					quantity: 13
				} as AmmoInstance
			]
		});
	}

	equipItem(itemId: number, slot: EquippedSlot): Observable<Inventory> {
		console.log(`Equipping item ${ itemId } in slot ${ slot }`);
		return this.getInventory().pipe(delay(500));
	}

	unequipItem(itemId: number): Observable<Inventory> {
		console.log(`Unequipping item ${ itemId }`);
		return this.getInventory().pipe(delay(500));
	}

	loadWeapon(weaponId: number, ammoId: number): Observable<Inventory> {
		console.log(`Loading weapon ${ weaponId } with ${ ammoId }`);
		return this.getInventory().pipe(delay(500));
	}

	unloadWeapon(weaponId: number): Observable<Inventory> {
		console.log(`Unloading weapon ${ weaponId }`);
		return this.getInventory().pipe(delay(500));
	}
}
