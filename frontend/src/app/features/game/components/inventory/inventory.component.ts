import { NgOptimizedImage } from '@angular/common';
import { Component, computed, effect, inject, signal } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import {
  ArmorInstance,
  EquippedSlot,
  ItemInstance,
  ItemType,
  WeaponInstance,
  WeaponMode,
  WeaponModeIcons
} from '@features/game/models/inventory/inventory.model';
import { InventoryService } from '@features/game/services/api/inventory.service';
import { CharacterStore } from '@features/game/stores/character.store';
import { AsItemInstancePipe } from '@shared/pipes/as-item-instance.pipe';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';
import { Character } from '../../models/character.model';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.scss',
  providers: [AsItemPipe],
  imports: [NgOptimizedImage, AsItemInstancePipe, AsItemPipe]
})
export class InventoryComponent {
  private readonly asItemPipe = inject(AsItemPipe);
  private readonly characterStore = inject(CharacterStore);
  private readonly inventoryService = inject(InventoryService);
  protected readonly EquippedSlot = EquippedSlot;
  protected readonly ItemType = ItemType;
  protected readonly WeaponModeIcons = WeaponModeIcons;

  // Signals
  readonly selectedItemInstance = signal<ItemInstance | null>(null);
  readonly showContextMenu = signal(false);
  readonly contextMenuPosition = signal<{ x: number; y: number } | null>(null);
  readonly character = signal<Character | null>(this.characterStore.character());

  readonly primaryWeaponMode = signal<WeaponMode | null | undefined>(null);
  readonly secondaryWeaponMode = signal<WeaponMode | null | undefined>(null);
  readonly contextMenuSlot = signal<EquippedSlot | null>(null);

  // Computed
  readonly armorInstance = computed(() => this.findEquipped<ArmorInstance>(EquippedSlot.ARMOR));
  readonly primaryWeaponInstance = computed(() => this.findEquipped<WeaponInstance>(EquippedSlot.PRIMARY_WEAPON));
  readonly secondaryWeaponInstance = computed(() => this.findEquipped<WeaponInstance>(EquippedSlot.SECONDARY_WEAPON));

  readonly primaryWeapon = computed(() => this.asItemPipe.transform(this.primaryWeaponInstance()?.item, ItemType.WEAPON));
  readonly secondaryWeapon = computed(() => this.asItemPipe.transform(this.secondaryWeaponInstance()?.item, ItemType.WEAPON));

  readonly selectedItem = computed(() => this.selectedItemInstance()?.item);

  readonly primaryWeaponModeIcon = computed(() => this.getWeaponIcon(this.primaryWeaponMode()));
  readonly secondaryWeaponModeIcon = computed(() => this.getWeaponIcon(this.secondaryWeaponMode()));

  readonly specialKeys = computed(() => {
    const special = this.character()?.special;
    return special ? (Object.keys(special) as (keyof Character['special'])[]) : [];
  });

  readonly inventory = toSignal(this.inventoryService.getInventory(), {
    initialValue: {
      id: 0,
      characterId: 0,
      currentWeight: 0,
      maxWeight: 0,
      items: []
    }
  });

  constructor() {
    effect(() => {
      // Init mode par défaut arme principale
      if (!this.primaryWeaponMode() && this.primaryWeapon()?.weaponModes.length) {
        this.primaryWeaponMode.set(this.primaryWeapon()?.weaponModes[0]);
      }
    });

    effect(() => {
      // Init mode par défaut arme secondaire
      if (!this.secondaryWeaponMode() && this.secondaryWeapon()?.weaponModes.length) {
        this.secondaryWeaponMode.set(this.secondaryWeapon()?.weaponModes[0]);
      }
    });
  }

  private getWeaponIcon(mode: WeaponMode | null | undefined): string {
    return mode?.modeType ? WeaponModeIcons[mode.modeType] : '';
  }

  /** Retourne l'item équipé pour le slot donné, ou null s'il est vide. */
  private findEquipped<T extends ItemInstance>(slot: EquippedSlot): T | null {
    return this.inventory().items.find(
      (item) => (item as ItemInstance & { equippedSlot: EquippedSlot }).equippedSlot === slot
    ) as T | null;
  }

  selectItem(item: ItemInstance | null) {
    this.selectedItemInstance.set(item);
  }

  showWeaponContextMenu(event: MouseEvent, slot: EquippedSlot) {
    event.preventDefault();
    if (this.contextMenuSlot() !== slot || !this.showContextMenu()) {
      this.contextMenuPosition.set({ x: event.clientX, y: event.clientY });
      this.contextMenuSlot.set(slot);
      this.showContextMenu.set(true);
    }
  }

  changeWeaponMode(mode: WeaponMode, equippedSlot: EquippedSlot) {
    if (equippedSlot === EquippedSlot.PRIMARY_WEAPON) {
      this.primaryWeaponMode.set(mode);
    } else if (equippedSlot === EquippedSlot.SECONDARY_WEAPON) {
      this.secondaryWeaponMode.set(mode);
    }
    this.showContextMenu.set(false);
  }

}
