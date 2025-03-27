import { Injectable } from '@angular/core';
import { Position } from '@features/game/models/position.model';
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
  getWorldPosition(position: Position): { x: number; y: number } | null {
    if (!this.board) return null;
    return this.board.tileXYToWorldXY(position.x, position.y);
  }
}
