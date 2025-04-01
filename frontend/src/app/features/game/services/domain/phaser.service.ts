import { inject, Injectable } from '@angular/core';
import { environment } from '@environments/environment.development';
import { Position } from '@features/game/models/position.model';
import { Tile } from '@features/game/models/tile.model';
import { PhaserStore } from '@features/game/stores/phaser.store';

@Injectable({
  providedIn: 'root'
})
export class PhaserService {
  private readonly phaserStore = inject(PhaserStore);

  /**
   * Convertit une position de grille en coordonnées du monde (pixels)
   * @param position Position {x, y} dans la grille
   * @returns Coordonnées monde {x, y} ou null si hors grille
   */
  getWorldPosition(position?: Position): Position | undefined {
    return this.phaserStore.board()?.tileXYToWorldXY(position?.x ?? 0, position?.y ?? 0);
  }

  /**
   * Retourne la texture à utiliser pour une tuile
   */
  getTextureForTile(tile: Tile): string {
    return tile.type.toLowerCase().replace(/_/g, '-');
  }

  /**
   * Dessine une bordure autour d'une tuile
   */
  drawTileBorder(x: number, y: number): void {
    const size = environment.scene.board.size;
    const g = this.phaserStore.existingScene().add.graphics({ lineStyle: { width: 1, color: 0xffffff, alpha: 0.9 } });
    g.strokeRect(x - size / 2, y - size / 2, size, size);
  }

  getPosition(position: Position): string {
    return `${ position.x }_${ position.y }`;
  }
}
