import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { DamageType } from '@features/game/models/inventory/inventory.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class DamageTypeRepository {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/damage-types';

	getAll(): Observable<DamageType[]> {
		return this.http.get<DamageType[]>(`${ this.API_URL }/all`);
	}
}
