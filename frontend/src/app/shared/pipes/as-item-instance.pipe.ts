import { Pipe, PipeTransform } from '@angular/core';
import {
  AmmoInstance,
  ArmorInstance,
  ItemInstance,
  ItemType,
  WeaponInstance
} from '@features/game/models/inventory/inventory.model';

@Pipe({ name: 'asItemInstance' })
export class AsItemInstancePipe implements PipeTransform {
  transform(instance: ItemInstance | null | undefined, type: ItemType.AMMO): AmmoInstance | null;
  transform(instance: ItemInstance | null | undefined, type: ItemType.ARMOR): ArmorInstance | null;
  transform(instance: ItemInstance | null | undefined, type: ItemType.WEAPON): WeaponInstance | null;
  transform(instance: ItemInstance | null | undefined, type: ItemType): ItemInstance | null {
    if (!instance || instance.item.type !== type) return null;
    return instance;
  }
}
