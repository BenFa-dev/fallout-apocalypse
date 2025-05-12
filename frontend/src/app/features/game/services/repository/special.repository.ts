import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Special } from '@features/game/models/special.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class SpecialRepository {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/specials';

	getAll(): Observable<Special[]> {
		return this.http.get<Special[]>(`${ this.API_URL }/all`);
	}
}
