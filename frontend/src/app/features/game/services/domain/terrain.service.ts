import { inject, Injectable } from '@angular/core';
import { MapStore } from '@features/game/stores/map.store';
import { TranslateService } from '@ngx-translate/core';
import * as Phaser from 'phaser';
import { GridService } from './grid.service';
import { TooltipService } from './tooltip.service';

/** Service responsable de la génération et de l'affichage des tuiles de terrain */
@Injectable({
  providedIn: 'root'
})
export class TerrainService {
  private scene!: Phaser.Scene;
  private readonly TILE_SIZE = 128;
  private tileSprites = new Map<string, Phaser.GameObjects.Sprite>();

  private readonly gridService = inject(GridService);
  private readonly mapStore = inject(MapStore);
  private readonly tooltipService = inject(TooltipService);
  private readonly translate = inject(TranslateService);

  initialize(scene: Phaser.Scene) {
    this.scene = scene;

    this.tooltipService.initialize(this.scene);

    this.displayTiles();

  }

  private displayTiles() {
    // Nettoie les sprites existants
    this.tileSprites.forEach(sprite => sprite.destroy());
    this.tileSprites.clear();

    const tiles = this.mapStore.tiles();
    if (tiles.length === 0) return;

    let createdTiles = 0;
    let invalidPositions = 0;

    tiles.forEach(tile => {
      const worldXY = this.gridService.getWorldXY(tile.position.x, tile.position.y);
      if (!worldXY) {
        invalidPositions++;
        return;
      }

      const textureName = tile.type.toLowerCase().replace(/_/g, '-');
      if (!this.scene.textures.exists(textureName)) {
        console.error(`❌ Texture manquante pour ${ textureName }`);
        return;
      }

      const sprite = this.scene.add.sprite(worldXY.x, worldXY.y, textureName);
      sprite.setDisplaySize(this.TILE_SIZE, this.TILE_SIZE);
      sprite.setDepth(-1);

      const terrainConfig = this.mapStore.terrainConfigMap()[tile.type.toLowerCase()];
      const description = terrainConfig.descriptions[this.translate.currentLang];

      this.tooltipService.createTooltip(sprite, `🪨 ${ description }\n📍 (${ tile.position.x };${ tile.position.y })`);

      this.tileSprites.set(`${ tile.position.x },${ tile.position.y }`, sprite);
      createdTiles++;
    });

    if (createdTiles !== tiles.length) {
      console.log(`⚠️ Tuiles créées: ${ createdTiles }/${ tiles.length }` +
        (invalidPositions ? ` - Positions invalides: ${ invalidPositions }` : ''));
    }
  }
}
