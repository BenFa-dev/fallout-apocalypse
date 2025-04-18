import { inject, Pipe, PipeTransform } from '@angular/core';
import { Ammo, Armor, Item, ItemType, Weapon } from '@features/game/models/inventory/inventory.model';
import { CastItemService } from '@features/game/services/domain/cast-item.service';

@Pipe({ name: 'asItem' })
export class AsItemPipe implements PipeTransform {
	private readonly cast = inject(CastItemService);

	transform(item: Item | null | undefined, type: ItemType.AMMO): Ammo | null;
	transform(item: Item | null | undefined, type: ItemType.ARMOR): Armor | null;
	transform(item: Item | null | undefined, type: ItemType.WEAPON): Weapon | null;
	transform(item: Item | null | undefined, type: ItemType): Item | null {
		return this.cast.asItem(item, type);
	}
}
