import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { DerivedStat } from '@features/game/models/derived-stat.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class DerivedStatRepository {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/derived-stats';

	getAll(): Observable<DerivedStat[]> {
		return this.http.get<DerivedStat[]>(`${ this.API_URL }/all`);
	}
}
