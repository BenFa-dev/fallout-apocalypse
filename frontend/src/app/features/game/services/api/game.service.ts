import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Character } from '@features/game/models/character.model';
import { Position } from '@features/game/models/position.model';
import { SpecialInstance } from '@features/game/models/special.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class GameService {
	private http = inject(HttpClient);
	private API_URL = '/api/characters';

	getCurrentCharacter(): Observable<Character> {
		return this.http.get<Character>(`${ this.API_URL }/current`);
	}

	createCharacter(name: string, special: SpecialInstance[]): Observable<Character> {
		return this.http.post<Character>(`${ this.API_URL }`, { name, special });
	}

	moveCharacter(targetPosition: Position): Observable<Character> {
		return this.http.post<Character>(`${ this.API_URL }/move`, targetPosition);
	}
}
