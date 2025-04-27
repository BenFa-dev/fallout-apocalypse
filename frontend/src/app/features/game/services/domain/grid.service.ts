import { effect, inject, Injectable } from '@angular/core';
import { environment } from '@environments/environment.development';
import { Position } from '@features/game/models/position.model';
import { Tile } from '@features/game/models/tile.model';
import { PhaserService } from '@features/game/services/domain/phaser.service';
import { PlayerService } from '@features/game/services/domain/player.service';
import { TooltipService } from '@features/game/services/domain/tooltip.service';
import { CharacterStore } from '@features/game/stores/character.store';
import { MapStore } from '@features/game/stores/map.store';
import { PhaserStore } from '@features/game/stores/phaser.store';
import { TranslateService } from '@ngx-translate/core';

const TILE_OPACITY_IN_VISION = 1;
const TILE_OPACITY_NOT_IN_VISION = 0.3;

@Injectable({ providedIn: 'root' })
export class GridService {
	private readonly characterStore = inject(CharacterStore);
	private readonly mapStore = inject(MapStore);
	private readonly phaserService = inject(PhaserService);
	private readonly phaserStore = inject(PhaserStore);
	private readonly playerService = inject(PlayerService);
	private readonly tooltip = inject(TooltipService);
	private readonly translate = inject(TranslateService);

	constructor() {
		effect(() => {
			const playerPosition = this.characterStore.playerPosition();
			const isInitialized = this.phaserStore.isInitialized();

			console.log(playerPosition, isInitialized);
			if (playerPosition != null && isInitialized) {
				console.log('🎮 Mise à jour de la position du joueur et des tuiles visibles');
				this.playerService.moveToPosition(playerPosition);
				this.updateTilesInVision();
				this.updateTilesOutOfView();
			}
		});
	}

	/**
	 * Initialisation des tuiles sur la carte lors du 1er chargement
	 * */
	public createGridTiles(): void {
		const scene = this.phaserStore.existingScene();
		const playerCurrentTilesInVision: Tile[] = [];

		for (const tile of this.mapStore.tiles()) {
			const tilePosition = this.phaserService.getWorldPosition(tile.position);
			if (!tilePosition) continue;

			tile.sprite = scene.add.sprite(tilePosition.x, tilePosition.y, this.phaserService.getTextureForTile(tile))
				.setDisplaySize(environment.scene.board.size, environment.scene.board.size)
				.setDepth(-1)
				.setAlpha(TILE_OPACITY_NOT_IN_VISION);

			if (tile.visible) {
				playerCurrentTilesInVision.push(tile);
			}
			this.phaserService.drawTileBorder(tilePosition.x, tilePosition.y);
			this.showTooltipIfAny(tile);
		}

		// Mise à jour des tuiles visibles
		this.characterStore.updatePlayerCurrentVision(playerCurrentTilesInVision);
	}

	public updateTilesInVision(): void {
		for (const tile of this.characterStore.currentTilesInVision()) {
			const existing = this.getTileAtPosition(tile.position);
			if (existing) {
				this.updateTileVisibility(existing, tile);
			}
		}
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
	private updateTilesOutOfView(): void {
		const previousTiles = this.characterStore.previousTilesInVision();
		const currentTiles = this.characterStore.currentTilesInVision();

		if (previousTiles.length === 0) return;

		// Pour exclure les tuiles encore visibles
		const currentTileIds = new Set(currentTiles.map(tile => tile.id));

		for (const tile of previousTiles) {
			if (!currentTileIds.has(tile.id)) {
				const mapTile = this.getTileAtPosition(tile.position);
				if (mapTile && mapTile.sprite) {
					this.setTileVisibility(mapTile, false);
				}
			}
		}
	}

	private setTileVisibility(tile: Tile, visible: boolean): void {
		tile.visible = visible;
		tile.sprite?.setAlpha(visible ? TILE_OPACITY_IN_VISION : TILE_OPACITY_NOT_IN_VISION);
	}

	private getTileAtPosition(position: Position): Tile | undefined {
		return this.mapStore.tilesMap().get(this.phaserService.getPosition(position));
	}

	/**
	 * Effet de découverte d'une tuile, avec changement de texture.
	 */
	private revealTile(tile: Tile): void {
		if (!tile.sprite) return;

		const sprite = tile.sprite;
		const scene = this.phaserStore.existingScene();

		sprite.setAlpha(0);
		sprite.setTexture(this.phaserService.getTextureForTile(tile));
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

	private showTooltipIfAny(tile: Tile): void {
		const desc = tile.descriptions?.[this.translate.currentLang];
		if (!desc || !tile.sprite) return;

		this.tooltip.createTooltip(
			tile.sprite,
			`🪨 ${ desc }\n📍 (${ tile.position.x }; ${ tile.position.y })`
		);
	}
}
