import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { TerrainConfiguration } from '@features/game/models/terrain-configuration.model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TerrainConfigurationService {
  private http = inject(HttpClient);
  private readonly API_URL = '/api/terrain-configurations';

  getTerrainConfigurations(): Observable<TerrainConfiguration[]> {
    return this.http.get<TerrainConfiguration[]>(this.API_URL);
  }
}
