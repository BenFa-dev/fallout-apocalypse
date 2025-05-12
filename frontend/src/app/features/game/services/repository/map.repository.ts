import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Map } from '@features/game/models/map.model';
import { Tile } from '@features/game/models/tile.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class MapRepository {
	private readonly http = inject(HttpClient);
	private readonly API_URL = '/api/maps';

	loadMap(mapId: number): Observable<Map> {
		return this.http.get<Map>(`${ this.API_URL }/${ mapId }`);
	}

	loadTiles(mapId: number): Observable<Tile[]> {
		return this.http.get<Tile[]>(`${ this.API_URL }/${ mapId }/tiles`);
	}

	discoverTiles(mapId: number, x: number, y: number): Observable<Tile[]> {
		return this.http.get<Tile[]>(`${ this.API_URL }/${ mapId }/discover`, {
			params: { x: x.toString(), y: y.toString() }
		});
	}
}
