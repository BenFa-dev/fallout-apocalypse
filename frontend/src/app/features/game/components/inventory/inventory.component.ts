import { Component, computed, effect, inject, signal, Output, EventEmitter } from '@angular/core';
import { toSignal } from '@angular/core/rxjs-interop';
import { MatButtonModule } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatGridListModule } from '@angular/material/grid-list';
import { MatTooltipModule } from '@angular/material/tooltip';
import { MatIconModule } from '@angular/material/icon';
import { MatToolbarModule } from '@angular/material/toolbar';
import { LanguageService } from '@core/services/language.service';
import {
  InventoryCharacterComponent
} from '@features/game/components/inventory/inventory-character/inventory-character.component';
import {
  InventoryDescriptionComponent
} from '@features/game/components/inventory/inventory-description/inventory-description.component';
import { InventoryListComponent } from '@features/game/components/inventory/inventory-list/inventory-list.component';
import { InventoryStatsComponent } from '@features/game/components/inventory/inventory-stats/inventory-stats.component';
import { Character } from '@features/game/models/character.model';
import {
  ArmorInstance,
  EquippedSlot,
  ItemInstance,
  ItemType,
  WeaponInstance,
  WeaponMode
} from '@features/game/models/inventory/inventory.model';
import { InventoryService } from '@features/game/services/api/inventory.service';
import { CharacterStore } from '@features/game/stores/character.store';
import { TranslateModule } from '@ngx-translate/core';
import { AsItemPipe } from '@shared/pipes/as-item.pipe';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrl: './inventory.component.scss',
  providers: [AsItemPipe],
  imports: [
    AsItemPipe,
    MatCardModule,
    MatButtonModule,
    MatDividerModule,
    TranslateModule,
    MatGridListModule,
    MatTooltipModule,
    MatIconModule,
    MatToolbarModule,
    InventoryListComponent,
    InventoryCharacterComponent,
    InventoryStatsComponent,
    InventoryDescriptionComponent
  ]
})
export class InventoryComponent {
  @Output() close = new EventEmitter<void>();

  private readonly asItemPipe = inject(AsItemPipe);
  private readonly characterStore = inject(CharacterStore);
  private readonly inventoryService = inject(InventoryService);
  private readonly languageService = inject(LanguageService);

  protected readonly ItemType = ItemType;
  protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

  // Signals
  readonly selectedItemInstance = signal<ItemInstance | null | undefined>(null);
  readonly character = signal<Character | null>(this.characterStore.character());

  readonly primaryWeaponMode = signal<WeaponMode | null | undefined>(null);
  readonly secondaryWeaponMode = signal<WeaponMode | null | undefined>(null);

  // Computed
  readonly armorInstance = computed(() => this.findEquipped<ArmorInstance>(EquippedSlot.ARMOR));
  readonly primaryWeaponInstance = computed(() => this.findEquipped<WeaponInstance>(EquippedSlot.PRIMARY_WEAPON));
  readonly secondaryWeaponInstance = computed(() => this.findEquipped<WeaponInstance>(EquippedSlot.SECONDARY_WEAPON));

  readonly primaryWeapon = computed(() => this.asItemPipe.transform(this.primaryWeaponInstance()?.item, ItemType.WEAPON));
  readonly secondaryWeapon = computed(() => this.asItemPipe.transform(this.secondaryWeaponInstance()?.item, ItemType.WEAPON));

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

  /** Retourne l'item équipé pour le slot donné, ou null s'il est vide. */
  private findEquipped<T extends ItemInstance>(slot: EquippedSlot): T | null {
    return this.inventory().items.find(
      (item) => (item as ItemInstance & { equippedSlot: EquippedSlot }).equippedSlot === slot
    ) as T | null;
  }

  selectItem(item: ItemInstance | null | undefined) {
    this.selectedItemInstance.set(item);
  }

  changeWeaponMode(event: { mode: WeaponMode; equippedSlot: EquippedSlot }) {
    if (event.equippedSlot === EquippedSlot.PRIMARY_WEAPON) {
      this.primaryWeaponMode.set(event.mode);
    } else if (event.equippedSlot === EquippedSlot.SECONDARY_WEAPON) {
      this.secondaryWeaponMode.set(event.mode);
    }
  }

  onClose() {
    this.close.emit();
  }
}
