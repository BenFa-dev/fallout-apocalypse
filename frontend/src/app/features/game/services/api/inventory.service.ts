import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { EquippedSlot, Inventory } from '@features/game/models/inventory/inventory.model';
import { delay, Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class InventoryService {
	private http = inject(HttpClient);
	private API_URL = '/api/player/inventory';

	getInventory(): Observable<Inventory> {
		return this.http.get<Inventory>(`${ this.API_URL }`);
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
