import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class PerkRepository {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/perks';

	getAllAvailable(): Observable<BaseNamedEntity[]> {
		return this.http.get<BaseNamedEntity[]>(`${ this.API_URL }/all-available`);
	}
}
