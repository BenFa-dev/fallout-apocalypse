import { Injectable } from '@angular/core';
import { environment } from '@environments/environment.development';
import { Position } from '@features/game/models/position.model';
import { Tile } from '@features/game/models/tile.model';
import { Scene } from 'phaser';
import { Board } from 'phaser3-rex-plugins/plugins/board-plugin';

@Injectable({
  providedIn: 'root'
})
export class PhaserService {

  private scene!: Scene;
  private board!: Board;

  initialize(scene: Scene, board: Board) {
    this.scene = scene;
    this.board = board;
    if (!this.board?.grid) {
      console.error('❌ Board ou grid non défini');
      return;
    }
  }

  getScene() {
    return this.scene;
  }

  getKeyboard() {
    return this.scene.input.keyboard!;
  }

  /**
   * Convertit une position de grille en coordonnées du monde (pixels)
   * @param position Position {x, y} dans la grille
   * @returns Coordonnées monde {x, y} ou null si hors grille
   */
  getWorldPosition(position: Position): { x: number; y: number } {
    return this.board.tileXYToWorldXY(position.x, position.y);
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
    const g = this.scene.add.graphics({ lineStyle: { width: 1, color: 0xffffff, alpha: 0.9 } });
    g.strokeRect(x - size / 2, y - size / 2, size, size);
  }
}
