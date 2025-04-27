import { computed, inject } from '@angular/core';
import { NotificationService } from '@core/services/notification.service';
import { Map } from '@features/game/models/map.model';
import { Position } from '@features/game/models/position.model';
import { TerrainConfiguration } from '@features/game/models/terrain-configuration.model';
import { Tile } from '@features/game/models/tile.model';
import { GameService } from '@features/game/services/api/game.service';
import { MapService } from '@features/game/services/api/map.service';
import { TerrainConfigurationService } from '@features/game/services/api/terrain-configuration.service';
import { PhaserService } from '@features/game/services/domain/phaser.service';
import { PlayerStore } from '@features/game/stores/player.store';
import { patchState, signalStore, withComputed, withHooks, withMethods, withProps, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, forkJoin, pipe, switchMap, tap } from 'rxjs';

// Permet de passer le store dans un constructeur
// https://github.com/ngrx/platform/discussions/4140
export type MapStore = InstanceType<typeof MapStore>;

type MapState = {
	isInitialized: boolean
	isLoading: boolean
	map: Map | null;
	tiles: Tile[];
	terrainConfigurations: TerrainConfiguration[],
	isMoving: boolean;
};

export const initialState: MapState = {
	isInitialized: false,
	isLoading: false,
	map: null,
	tiles: [],
	terrainConfigurations: [],
	isMoving: false
};

export const MapStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withProps(() => ({
		_playerStore: inject(PlayerStore)
	})),
	withComputed((store, phaserService = inject(PhaserService)) => ({
		terrainConfigMap: computed(() => {
			const map: Record<string, TerrainConfiguration> = {};
			store.terrainConfigurations().forEach((config: TerrainConfiguration) => {
				map[config.name.toLowerCase()] = config;
			});
			return map;
		}),
		tilesMap: computed(() =>
			new Map(
				store.tiles().map(tile => [phaserService.getPosition(tile.position), tile])
			)
		)
	})),
	withMethods((
		store,
		gameService = inject(GameService),
		mapService = inject(MapService),
		notificationService = inject(NotificationService),
		terrainConfigurationService = inject(TerrainConfigurationService)
	) => {

		const loadMap = rxMethod<void>(
			pipe(
				debounceTime(300),
				tap(() => patchState(store, { isLoading: true })),
				switchMap(() =>
					forkJoin({
						map: mapService.loadMap(1),
						tiles: mapService.loadTiles(1),
						terrainConfigurations: terrainConfigurationService.getTerrainConfigurations()
					}).pipe(
						tap({
							next: ({ map, tiles, terrainConfigurations }) => {
								patchState(store, { map, tiles, terrainConfigurations, isInitialized: true });
							},
							error: () => {
								console.error('❌ Erreur lors du chargement des données de la carte');
							},
							finalize: () => patchState(store, { isLoading: false })
						})
					)
				)
			)
		);

		const discoverTiles = rxMethod<Position>(
			pipe(
				switchMap((position) =>
					mapService.discoverTiles(1, position.x, position.y).pipe(
						tap({
							next: (newTiles) => {
								store._playerStore.updatePlayerCurrentVision(newTiles);
							},
							error: (error) => {
								console.error('❌ Erreur lors de la découverte des tuiles:', error);
								notificationService.showNotification("error", 'game.tiles.errors.discovery');
							}
						})
					)
				)
			)
		);

		const handleMovement = rxMethod<Position>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				tap(() => patchState(store, { isMoving: true })),
				switchMap((newPosition) =>
					gameService.moveCharacter(newPosition).pipe(
						tap({
							next: (updatedCharacter) => {
								store._playerStore.updateCharacter(updatedCharacter);
							},
							error: (error) => {
								console.error('❌ Erreur lors du déplacement:', error);
								notificationService.showNotification("error", 'game.movement.errors.unknown');
							},
							finalize: () => patchState(store, { isMoving: false })
						}),
						tap(() => discoverTiles(newPosition))
					)
				)
			)
		);

		return {
			loadMap,
			discoverTiles,
			handleMovement
		};
	}),
	withHooks({
		onInit(store) {
			store.loadMap();
		}
	})
);
