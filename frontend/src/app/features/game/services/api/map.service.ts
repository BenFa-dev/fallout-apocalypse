import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Map } from '@features/game/models/map.model';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class MapService {
  private readonly http = inject(HttpClient);
  private readonly API_URL = '/api/maps';

  loadMap(mapId: number): Observable<Map> {
    return this.http.get<Map>(`${ this.API_URL }/${ mapId }`);
  }
}
