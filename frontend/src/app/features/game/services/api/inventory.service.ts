import { Injectable } from '@angular/core';
import {
  AmmoInstance,
  ArmorInstance,
  DamageType,
  EquippedSlot,
  Inventory,
  ItemType,
  WeaponInstance,
  WeaponModeType,
  WeaponType
} from '@features/game/models/inventory/inventory.model';
import { Observable, of } from 'rxjs';

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
            weaponType: WeaponType.SMALL_GUN,
            damageType: DamageType.NORMAL,
            requiredStrength: 2,
            requiredHands: 1,
            names: { en: '10mm Pistol', fr: 'Pistolet 10mm' },
            descriptions: { en: '', fr: '' },
            weight: 3,
            basePrice: 250,
            path: 'assets/items/10mm_pistol.webp',
            capacity: 8,
            weaponModes: [
              {
                id: 2,
                modeType: WeaponModeType.AIMED,
                actionPoints: 6,
                minDamage: 5,
                maxDamage: 15,
                range: 5
              },
              {
                id: 1,
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
          equippedSlot: EquippedSlot.SECONDARY_WEAPON
        } as WeaponInstance,
        {
          id: 2,
          item: {
            id: 2,
            type: ItemType.ARMOR,
            names: { en: 'Leather Jacket', fr: 'Veste en cuir' },
            descriptions: { en: '', fr: '' },
            weight: 8,
            basePrice: 200,
            path: 'assets/items/leather_armor.webp',
            armorClass: 8,
            damageThresholdNormal: 0,
            damageThresholdLaser: 0,
            damageThresholdFire: 0,
            damageThresholdPlasma: 0,
            damageThresholdExplosive: 0,
            damageThresholdElectric: 0,
            damageResistanceNormal: 0,
            damageResistanceLaser: 0,
            damageResistanceFire: 0,
            damageResistancePlasma: 0,
            damageResistanceExplosive: 0,
            damageResistanceElectric: 0
          },
          equippedSlot: EquippedSlot.ARMOR
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
}
