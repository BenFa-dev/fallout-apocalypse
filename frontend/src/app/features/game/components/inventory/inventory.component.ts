import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { Character, Special } from '@features/game/models/character.model';
import {
  AmmoInstance,
  Armor,
  ArmorInstance,
  EquippedSlot,
  Item,
  ItemInstance,
  ItemType,
  Weapon,
  WeaponInstance
} from '@features/game/models/inventory/inventory.model';
import { InventoryService } from '@features/game/services/api/inventory.service';
import { CharacterStore } from '@features/game/stores/character.store';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.scss',
  imports: [
    NgOptimizedImage
  ]
})
export class InventoryComponent {
  private characterStore = inject(CharacterStore);
  private inventoryService = inject(InventoryService);

  readonly selectedItem = signal<ItemInstance | null>(null);
  readonly character = signal<Character | null>(this.characterStore.character());

  readonly inventory = toSignal(this.inventoryService.getInventory(), {
    initialValue: {
      id: 0,
      characterId: 0,
      currentWeight: 0,
      maxWeight: 0,
      items: []
    }
  });

  readonly primaryEquippedWeapon = computed(() =>
    this.inventory().items.find(
      (i): i is WeaponInstance =>
        i.item.type === ItemType.WEAPON && (i as WeaponInstance).equippedSlot === EquippedSlot.PRIMARY_WEAPON
    ) ?? null
  );

  readonly secondaryEquippedWeapon = computed(() =>
    this.inventory().items.find(
      (i): i is WeaponInstance =>
        i.item.type === ItemType.WEAPON && (i as WeaponInstance).equippedSlot === EquippedSlot.SECONDARY_WEAPON
    ) ?? null
  );

  readonly equippedArmor = computed(() =>
    this.inventory().items.find(
      (i): i is ArmorInstance =>
        i.item.type === ItemType.ARMOR && (i as ArmorInstance).equippedSlot === EquippedSlot.ARMOR
    ) ?? null
  );

  selectItem(item?: ItemInstance | null): void {
    if (item) this.selectedItem.set(item);
  }

  specialKeys(special: Special): (keyof Special)[] {
    return Object.keys(special) as (keyof Special)[];
  }

  asAmmoInstance = (item: ItemInstance | null) => item as AmmoInstance;
  asWeaponInstance = (item: ItemInstance | null) => item as WeaponInstance;

  asWeapon = (item: Item) => item as Weapon;
  asArmor = (item: Item) => item as Armor;
}
