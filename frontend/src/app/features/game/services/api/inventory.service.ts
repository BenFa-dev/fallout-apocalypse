import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Character } from '@features/game/models/character.model';
import { EquippedSlot } from '@features/game/models/inventory/inventory.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class InventoryService {
	private http = inject(HttpClient);
	private API_URL = '/api/player/inventory';

	getCharacterInventory(): Observable<Character> {
		return this.http.get<Character>(`${ this.API_URL }`);
	}

	equipItem(itemId: number, slot: EquippedSlot): Observable<Character> {
		console.log(`Equipping item ${ itemId } in slot ${ slot }`);
		return this.http.post<Character>(
			`${ this.API_URL }/equip/${ itemId }?slot=${ slot }`,
			{}
		);
	}

	unequipItem(itemId: number): Observable<Character> {
		console.log(`Unequipping item ${ itemId }`);
		return this.http.post<Character>(
			`${ this.API_URL }/unequip/${ itemId }`,
			{}
		);
	}

	loadWeapon(weaponId: number, ammoId: number): Observable<Character> {
		console.log(`Loading weapon ${ weaponId } with ${ ammoId }`);
		return this.http.post<Character>(
			`${ this.API_URL }/reload/${ weaponId }/${ ammoId }`,
			{}
		);
	}

	unloadWeapon(weaponId: number): Observable<Character> {
		console.log(`Unloading weapon ${ weaponId }`);
		return this.http.post<Character>(
			`${ this.API_URL }/unload/${ weaponId }`,
			{}
		);
	}

	changeWeaponMode(weaponId: number, modeId: number): Observable<Character> {
		console.log(`Change weapon mode ${ modeId } of ${ weaponId }`);
		return this.http.post<Character>(
			`${ this.API_URL }/weapon-mode/${ weaponId }/${ modeId }`,
			{}
		);
	}
}
