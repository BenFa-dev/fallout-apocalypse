import { computed, inject } from '@angular/core';
import { Character, CharacterSheet } from '@features/game/models/character.model';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { Perk } from '@features/game/models/perk.model';
import { Skill } from '@features/game/models/skill.model';
import { Special } from '@features/game/models/special.model';
import { Tile } from '@features/game/models/tile.model';
import { GameService } from '@features/game/services/api/game.service';
import { PerkService } from '@features/game/services/api/perk.service';
import { SkillService } from '@features/game/services/api/skill.service';
import { patchState, signalStore, withComputed, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, pipe, switchMap, tap } from 'rxjs';
import { SpecialService } from '../services/api/special.service';

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
	perks: Perk[];
	specials: Special[];
	characterSheet: CharacterSheet | null
};

const initialState: PlayerState = {
	isInitialized: false,
	isLoading: false,
	player: null,
	currentTile: null,
	currentTilesInVision: [],
	previousTilesInVision: [],
	skills: [],
	perks: [],
	specials: [],
	characterSheet: null
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
		stats: computed(() => store.player()?.stats),
		currentStats: computed(() => store.player()?.currentStats),
		skillsInstances: computed(() =>
			new Map((store.player()?.skills ?? []).map(skill => [skill.skillId, skill]))
		),
		specialsInstances: computed(() =>
			new Map((store.player()?.specials ?? []).map(special => [special.specialId, special]))
		),
		selectedItem: computed(() => store.characterSheet()?.selectedItem)
	})),
	withMethods((
		store,
		gameService = inject(GameService),
		skillService = inject(SkillService),
		perkService = inject(PerkService),
		specialService = inject(SpecialService)
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

		loadSpecials: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				switchMap(() =>
					specialService.getAll().pipe(
						tap({
							next: (specials) => {
								console.log('🗺️ SPECIAL chargé');
								patchState(store, { specials })
							},
							error: () => {
								console.error('❌ Erreur lors du chargement du SPECIAL:');
							}
						})
					)
				)
			)
		),

		loadPerks: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				switchMap(() =>
					perkService.getAllAvailable().pipe(
						tap({
							next: (perks) => {
								console.log('🗺️ Avantages disponibles chargés');
								patchState(store, { perks })
							},
							error: () => {
								console.error('❌ Erreur lors du chargement des avantages:');
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

		open: () => patchState(store, {
			characterSheet: {
				isOpen: true,
				...store.characterSheet()
			}
		}),

		close: () => patchState(store, {
			characterSheet: {
				isOpen: false,
				...store.characterSheet()
			}
		}),

		updateSelectItem: (selectedItem: BaseNamedEntity) => patchState(store, {
			characterSheet: {
				selectedItem,
				isOpen: true
			}
		})
	})),
	withHooks({
		onInit(store) {
			store.loadSkills();
			store.loadSpecials();
			store.loadPerks();
		}
	})
);

