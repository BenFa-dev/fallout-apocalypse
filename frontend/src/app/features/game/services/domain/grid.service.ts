import { inject, Injectable } from '@angular/core';
import { environment } from '@environments/environment.development';
import { Tile } from '@features/game/models/tile.model';
import { PhaserService } from '@features/game/services/domain/phaser.service';
import { TooltipService } from '@features/game/services/domain/tooltip.service';
import { MapStore } from '@features/game/stores/map.store';
import { TranslateService } from '@ngx-translate/core';
import { Scene } from 'phaser';

@Injectable({
  providedIn: 'root'
})
export class GridService {
  private readonly mapStore = inject(MapStore);
  private readonly phaserService = inject(PhaserService);
  private readonly tooltipService = inject(TooltipService);
  private readonly translate = inject(TranslateService);

  /** Crée les lignes de la grille pour visualiser les cases */
  public createGridLines(): void {
    const scene = this.phaserService.getScene();
    const tiles = this.mapStore.tiles();
    if (!tiles.length) return;

    tiles.forEach(tile => {
      this.createTile(scene, tile);
    });
  }

  private createTile(scene: Scene, tile: Tile) {
    const worldXY = this.phaserService.getWorldPosition(tile.position);
    if (!worldXY) return;

    const textureName = tile.type.toLowerCase().replace(/_/g, '-');
    if (!scene.textures.exists(textureName)) {
      console.error(`❌ Texture manquante pour ${ textureName }`);
      return;
    }

    const sprite = scene.add.sprite(worldXY.x, worldXY.y, textureName)
      .setDisplaySize(environment.scene.board.size, environment.scene.board.size)
      .setDepth(-1);

    this.drawTileBorder(scene, worldXY.x, worldXY.y);

    const terrainConfig = this.mapStore.terrainConfigMap()[tile.type.toLowerCase()];
    const description = terrainConfig.descriptions[this.translate.currentLang];

    this.tooltipService.createTooltip(sprite, `🪨 ${ description }\n📍 (${ tile.position.x };${ tile.position.y })`);

  }

  private drawTileBorder(scene: Scene, worldX: number, worldY: number): void {
    const tileSize = environment.scene.board.size;

    const graphics = scene.add.graphics({ lineStyle: { width: 1, color: 0xffffff, alpha: 0.9 } });
    graphics.strokeRect(
      worldX - tileSize / 2,
      worldY - tileSize / 2,
      tileSize,
      tileSize
    );
  }

}
