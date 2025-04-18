import { inject, Pipe, PipeTransform } from '@angular/core';
import {
	AmmoInstance,
	ArmorInstance,
	ItemInstance,
	ItemType,
	WeaponInstance
} from '@features/game/models/inventory/inventory.model';
import { CastItemService } from '@features/game/services/domain/cast-item.service';

@Pipe({ name: 'asItemInstance' })
export class AsItemInstancePipe implements PipeTransform {
	private readonly cast = inject(CastItemService);

	transform(instance: ItemInstance | null | undefined, type: ItemType.AMMO): AmmoInstance | null;
	transform(instance: ItemInstance | null | undefined, type: ItemType.ARMOR): ArmorInstance | null;
	transform(instance: ItemInstance | null | undefined, type: ItemType.WEAPON): WeaponInstance | null;
	transform(instance: ItemInstance | null | undefined, type: ItemType): ItemInstance | null {
		return this.cast.asItemInstance(instance, type);
	}
}
