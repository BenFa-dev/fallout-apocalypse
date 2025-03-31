import { inject, Injectable } from '@angular/core';
import { environment } from '@environments/environment.development';
import { Tile } from '@features/game/models/tile.model';
import { PhaserService } from '@features/game/services/domain/phaser.service';
import { TooltipService } from '@features/game/services/domain/tooltip.service';
import { TranslateService } from '@ngx-translate/core';

const TILE_OPACITY_IN_VISION = 1;
const TILE_OPACITY_NOT_IN_VISION = 0.3;

@Injectable({ providedIn: 'root' })
export class GridService {
  private readonly phaser = inject(PhaserService);
  private readonly tileMap = new Map<string, Tile>();
  private readonly tooltip = inject(TooltipService);
  private readonly translate = inject(TranslateService);
  private currentlyVisibleKeys = new Set<string>();

  /**
   * Initialisation des tuiles sur la carte lors du 1er chargement
   * */
  public createGridLines(tiles: Tile[]): void {
    const scene = this.phaser.getScene();

    for (const tile of tiles) {
      const tilePosition = this.phaser.getWorldPosition(tile.position);
      if (!tilePosition) continue;

      const key = this.getKey(tile);

      tile.sprite = scene.add.sprite(tilePosition.x, tilePosition.y, this.phaser.getTextureForTile(tile))
        .setDisplaySize(environment.scene.board.size, environment.scene.board.size)
        .setDepth(-1)
        .setAlpha(tile.visible ? TILE_OPACITY_IN_VISION : TILE_OPACITY_NOT_IN_VISION);

      this.tileMap.set(key, tile);

      if (tile.visible) {
        this.currentlyVisibleKeys.add(key);
      }

      this.phaser.drawTileBorder(tilePosition.x, tilePosition.y);
      this.showTooltipIfAny(tile);
    }
  }

  /**
   * Met à jour uniquement la visibilité des tuiles impactées par le déplacement
   * */
  public applyTilesVisibility(tiles: Tile[]): void {
    const newVisibleKeys = new Set<string>();

    for (const tile of tiles) {
      const key = this.getKey(tile);
      const existing = this.tileMap.get(key);
      if (!existing) continue;

      newVisibleKeys.add(key);
      this.updateTileVisibility(existing, tile);
    }

    this.updateTilesOutOfView(newVisibleKeys);
    this.currentlyVisibleKeys = newVisibleKeys;
  }

  /**
   * Mise à jour du statut d'une nouvelle tuile (révélation, opacité, texture)
   **/
  private updateTileVisibility(existingTile: Tile, newTile: Tile): void {
    const shouldReveal = !existingTile.revealed && newTile.revealed;

    Object.assign(existingTile, {
      type: newTile.type,
      descriptions: newTile.descriptions,
      revealed: newTile.revealed
    });

    if (shouldReveal) {
      this.revealTile(existingTile);
    } else {
      this.setTileVisibility(existingTile, newTile.visible);
    }
  }

  /**
   * Met à jour les tuiles sorties du champ de vision
   **/
  private updateTilesOutOfView(newVisibleKeys: Set<string>): void {
    this.currentlyVisibleKeys.forEach(key => {
      if (!newVisibleKeys.has(key)) {
        const tile = this.tileMap.get(key);
        if (tile?.visible && tile.sprite) {
          this.setTileVisibility(tile, false);
        }
      }
    });
  }

  private setTileVisibility(tile: Tile, visible: boolean): void {
    tile.visible = visible;
    tile.sprite?.setAlpha(visible ? TILE_OPACITY_IN_VISION : TILE_OPACITY_NOT_IN_VISION);
  }

  /**
   * Effet de découverte d'une tuile, avec changement de texture.
   */
  private revealTile(tile: Tile): void {
    if (!tile.sprite) return;

    const sprite = tile.sprite;
    const scene = this.phaser.getScene();

    sprite.setAlpha(0);
    sprite.setTexture(this.phaser.getTextureForTile(tile));
    sprite.setDisplaySize(environment.scene.board.size, environment.scene.board.size);
    sprite.setVisible(true);

    scene.tweens.add({
      targets: sprite,
      alpha: 1,
      duration: 500,
      ease: 'Sine.easeInOut',
      onComplete: () => {
        tile.revealed = true;
        this.showTooltipIfAny(tile);
      }
    });
  }

  private getKey(tile: Tile): string {
    return `${ tile.position.x }_${ tile.position.y }`;
  }

  private showTooltipIfAny(tile: Tile): void {
    const desc = tile.descriptions?.[this.translate.currentLang];
    if (!desc || !tile.sprite) return;

    this.tooltip.createTooltip(
      tile.sprite,
      `🪨 ${ desc }\n📍 (${ tile.position.x }; ${ tile.position.y })`
    );
  }
}
