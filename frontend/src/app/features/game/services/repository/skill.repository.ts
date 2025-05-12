import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Skill } from '@features/game/models/skill.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class SkillRepository {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/skills';

	getAll(): Observable<Skill[]> {
		return this.http.get<Skill[]>(`${ this.API_URL }/all`);
	}
}
