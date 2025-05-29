import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Condition } from '@features/game/models/condition.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class ConditionRepository {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/conditions';

	getAll(): Observable<Condition[]> {
		return this.http.get<Condition[]>(`${ this.API_URL }/all`);
	}
}
