import { computed, inject } from '@angular/core';
import { Character } from '@features/game/models/character.model';
import { Skill } from '@features/game/models/skill.model';
import { Tile } from '@features/game/models/tile.model';
import { GameService } from '@features/game/services/api/game.service';
import { SkillService } from '@features/game/services/api/skill.service';
import { patchState, signalStore, withComputed, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, pipe, switchMap, tap } from 'rxjs';

// Permet de passer le store dans un constructeur
// https://github.com/ngrx/platform/discussions/4140
export type PlayerStore = InstanceType<typeof PlayerStore>;

type PlayerState = {
	isInitialized: boolean
	isLoading: boolean,
	player: Character | null;
	currentTile: Tile | null;
	currentTilesInVision: Tile[];
	previousTilesInVision: Tile[];
	skills: Skill[];
	isOpen: boolean;
};

const initialState: PlayerState = {
	isInitialized: false,
	isLoading: false,
	player: null,
	currentTile: null,
	currentTilesInVision: [],
	previousTilesInVision: [],
	skills: [],
	isOpen: false
};

export const PlayerStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withComputed((store) => ({
		hasCharacter: computed(() => store.player() !== null),
		canMove: computed(() => (store.player()?.currentStats?.actionPoints ?? 0) > 0),
		playerPosition: computed(() => ({
			x: store.player()?.currentX ?? 0,
			y: store.player()?.currentY ?? 0
		})),
		special: computed(() =>
			Object.entries(store.player()?.special ?? {}).map(([key, value]) => ({ key, value }))
		),
		stats: computed(() => store.player()?.stats),
		currentStats: computed(() => store.player()?.currentStats),
		skillsInstances: computed(() =>
			new Map((store.player()?.skills ?? []).map(skill => [skill.skillId, skill]))
		)
	})),
	withMethods((
		store,
		gameService = inject(GameService),
		skillService = inject(SkillService)
	) => ({

		updatePlayerCurrentTile(currentTile: Tile): void {
			patchState(store, { currentTile });
		},

		updatePlayerCurrentVision(currentTilesInVision: Tile[]): void {
			patchState(store, { previousTilesInVision: store.currentTilesInVision(), currentTilesInVision });
		},

		loadPlayer: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				tap(() => patchState(store, { isLoading: true })),
				switchMap(() =>
					gameService.getCurrentCharacter().pipe(
						tap({
							next: (updatedPlayer) => {
								console.log('🗺️ Joueur chargé');
								patchState(store, {
									player: updatedPlayer,
									isInitialized: true
								})
							},
							error: () => {
								console.error('❌ Erreur lors du chargement du joueur:');
							},
							finalize: () => {
								patchState(store, { isLoading: false })
							}
						})
					)
				)
			)
		),

		loadSkills: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				switchMap(() =>
					skillService.getAll().pipe(
						tap({
							next: (skills) => {
								console.log('🗺️ Compétences chargés');
								patchState(store, { skills })
							},
							error: () => {
								console.error('❌ Erreur lors du chargement des compétences:');
							}
						})
					)
				)
			)
		),

		updateCharacter(player?: Character) {
			if (!player) return;

			patchState(store, {
				player
			});
		},

		open: () => patchState(store, { isOpen: true }),

		close: () => patchState(store, { isOpen: false })
	})),
	withHooks({
		onInit(store) {
			store.loadSkills();
		}
	})
);

