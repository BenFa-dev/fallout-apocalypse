import { Pipe, PipeTransform } from '@angular/core';
import { Ammo, Armor, Item, ItemType, Weapon } from '@features/game/models/inventory/inventory.model';

@Pipe({ name: 'asItem' })
export class AsItemPipe implements PipeTransform {
  transform(item: Item | null | undefined, type: ItemType.AMMO): Ammo | null;
  transform(item: Item | null | undefined, type: ItemType.ARMOR): Armor | null;
  transform(item: Item | null | undefined, type: ItemType.WEAPON): Weapon | null;
  transform(item: Item | null | undefined, type: ItemType): Item | null {
    if (!item || item.type !== type) return null;
    return item;
  }
}
 
