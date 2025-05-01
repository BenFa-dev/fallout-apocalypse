import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Perk } from '@features/game/models/perk.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class PerkService {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/perks';

	getAllAvailable(): Observable<Perk[]> {
		return this.http.get<Perk[]>(`${ this.API_URL }/all-available`);
	}
}
