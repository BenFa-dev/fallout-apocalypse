import { computed, inject } from '@angular/core';
import {
	Character,
	CharacterCurrentStats,
	CharacterSheet,
	CharacterStats
} from '@features/game/models/character.model';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { PerkInstance } from '@features/game/models/perk.model';
import { SkillInstance } from '@features/game/models/skill.model';
import { SpecialInstance } from '@features/game/models/special.model';
import { Tile } from '@features/game/models/tile.model';
import { GameService } from '@features/game/services/api/game.service';
import { patchState, signalStore, withComputed, withMethods, withState } from '@ngrx/signals';
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
	skillInstances: SkillInstance[];
	perkInstances: PerkInstance[];
	specialInstances: SpecialInstance[];
	characterSheet: CharacterSheet | null
	stats: CharacterStats | null;
	currentStats: CharacterCurrentStats | null;
};

const initialState: PlayerState = {
	isInitialized: false,
	isLoading: false,
	player: null,
	currentTile: null,
	currentTilesInVision: [],
	previousTilesInVision: [],
	skillInstances: [],
	perkInstances: [],
	specialInstances: [],
	characterSheet: null,
	stats: null,
	currentStats: null
};

export const PlayerStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withComputed((store) => ({
		hasCharacter: computed(() => store.player() !== null),
		canMove: computed(() => true),
		playerPosition: computed(() => ({
			x: store.player()?.currentX ?? 0,
			y: store.player()?.currentY ?? 0
		})),
		skillsInstances: computed(() =>
			new Map((store?.skillInstances() ?? []).map(skill => [skill.skillId, skill]))
		),
		specialsInstances: computed(() =>
			new Map((store?.specialInstances() ?? []).map(special => [special.specialId, special]))
		),
		selectedItem: computed(() => store.characterSheet()?.selectedItem)
	})),
	withMethods((
		store,
		gameService = inject(GameService)
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

		updateCharacter(player?: Character) {
			if (!player) return;
			patchState(store, { player });
		},

		updatePlayerState({
			                  stats,
			                  currentStats,
			                  characterSheet,
			                  skillInstances,
			                  perkInstances,
			                  specialInstances
		                  }: Partial<PlayerState>) {
			console.log("updatePlayerState", stats?.armorClass)

			patchState(store, {
				...(stats && { stats: { ...stats } }),
				...(currentStats && { currentStats: { ...currentStats } }),
				...(characterSheet && { characterSheet: { ...characterSheet } }),
				...(skillInstances && { skillInstances: [...skillInstances] }),
				...(perkInstances && { perkInstances: [...perkInstances] }),
				...(specialInstances && { specialInstances: [...specialInstances] })
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
	}))
);

