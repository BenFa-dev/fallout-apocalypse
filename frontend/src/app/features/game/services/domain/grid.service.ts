import { Injectable } from '@angular/core';
import { Position } from '@features/game/models/position.model';
import * as Phaser from 'phaser';
import { Scene } from 'phaser';
import { Board } from 'phaser3-rex-plugins/plugins/board-plugin';

@Injectable({
  providedIn: 'root'
})
export class GridService {
  private graphics!: Phaser.GameObjects.Graphics;
  private readonly gridSize: number = 15;
  private scene!: Scene;
  private board!: Board;

  /** Initialise le service de grille avec la scène et le plateau
   * @param scene La scène de jeu
   * @param board Le plateau de jeu
   */
  initialize(scene: Scene, board: Board) {
    this.scene = scene;
    this.board = board;
    if (!this.board?.grid) {
      console.error('❌ Board ou grid non défini');
      return;
    }
    this.createGridLines();
  }

  /** Convertit une position de la grille en coordonnées monde
   * @param x Position X dans la grille
   * @param y Position Y dans la grille
   * @returns Coordonnées monde ou null si hors grille
   */
  getWorldXY(x: number, y: number) {
    if (!this.board) return null;
    return this.board.tileXYToWorldXY(x, y);
  }

  /** Crée les lignes de la grille pour visualiser les cases */
  private createGridLines() {
    this.graphics = this.scene.add.graphics();
    this.graphics.lineStyle(1, 0x00ff00, 0.3);

    this.generateTilePositions().forEach(pos => {

      const worldXY = this.getWorldXY(pos.x, pos.y);
      if (worldXY) {
        this.drawGridLinesForTile(worldXY);
      }
    });
  }

  /** Dessine les lignes d'une case de la grille
   * @param worldXY Position de la case en coordonnées monde
   */
  private drawGridLinesForTile(worldXY: { x: number; y: number }) {
    this.graphics.lineStyle(1, 0x00ff00, 0.3);
    this.graphics.moveTo(worldXY.x - 32, worldXY.y - 32);
    this.graphics.lineTo(worldXY.x + 32, worldXY.y - 32);
    this.graphics.moveTo(worldXY.x - 32, worldXY.y - 32);
    this.graphics.lineTo(worldXY.x - 32, worldXY.y + 32);
    this.graphics.moveTo(worldXY.x - 32, worldXY.y - 32);
    this.graphics.lineTo(worldXY.x + 32, worldXY.y + 32);
  }

  /** Génère toutes les positions de la grille
   * @returns Tableau des positions de toutes les cases de la grille
   */
  generateTilePositions(): Position[] {
    const positions: Position[] = [];
    const halfSize = Math.floor(this.gridSize / 2);
    for (let x = -halfSize; x <= halfSize; x++) {
      for (let y = -halfSize; y <= halfSize; y++) {
        positions.push({ x, y });
      }
    }
    return positions;
  }

}
