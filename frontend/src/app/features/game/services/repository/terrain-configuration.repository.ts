import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { TerrainConfiguration } from '@features/game/models/terrain-configuration.model';
import { Observable } from 'rxjs';

@Injectable({
	providedIn: 'root'
})
export class TerrainConfigurationRepository {
	private http = inject(HttpClient);
	private readonly API_URL = '/api/terrain-configurations';

	getTerrainConfigurations(): Observable<TerrainConfiguration[]> {
		return this.http.get<TerrainConfiguration[]>(this.API_URL);
	}
}
