import { Pipe, PipeTransform } from '@angular/core';
import { ArmorDetail, ItemDetail, ItemType, WeaponDetail } from '@features/game/models/inventory/inventory.model';

@Pipe({ name: 'asItemDetail' })
export class AsItemDetailPipe implements PipeTransform {
	transform(detail: ItemDetail | null | undefined, type: ItemType.ARMOR): ArmorDetail | null;
	transform(detail: ItemDetail | null | undefined, type: ItemType.WEAPON): WeaponDetail | null;
	transform(detail: ItemDetail | null | undefined, type: ItemType): ItemDetail | null {
		if (!detail || detail?.item?.type !== type) return null;
		return detail;
	}
}
