import { computed, inject } from '@angular/core';
import { Character, CharacterCurrentStats, CharacterSheet } from '@features/game/models/character.model';
import {
	BaseNamedBooleanInstance,
	BaseNamedEntity,
	BaseNamedIntegerInstance,
	BaseNamedTaggedInstance
} from '@features/game/models/common/base-named.model';
import { DerivedStatEnum } from '@features/game/models/derived-stat.model';
import { Tile } from '@features/game/models/tile.model';
import { GameRepository } from '@features/game/services/repository/game.repository';
import { GameStore } from '@features/game/stores/game.store';
import { patchState, signalStore, withComputed, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, pipe, switchMap, tap } from 'rxjs';

type PlayerState = {
	characterSheet: CharacterSheet | null
	conditionInstances: BaseNamedBooleanInstance[] | null;
	currentStats: CharacterCurrentStats | null;
	currentTile: Tile | null;
	currentTilesInVision: Tile[];
	derivedStatInstances: BaseNamedIntegerInstance[] | null;
	isInitialized: boolean
	isLoading: boolean,
	perkInstances: BaseNamedTaggedInstance[];
	player: Character | null;
	previousTilesInVision: Tile[];
	skillInstances: BaseNamedTaggedInstance[];
	specialInstances: BaseNamedIntegerInstance[];
};

const initialState: PlayerState = {
	characterSheet: null,
	conditionInstances: [],
	currentStats: null,
	currentTile: null,
	currentTilesInVision: [],
	derivedStatInstances: [],
	isInitialized: false,
	isLoading: false,
	perkInstances: [],
	player: null,
	previousTilesInVision: [],
	skillInstances: [],
	specialInstances: []
};

function toMap<T extends { id: number }>(items: T[] | null | undefined): Map<number, T> {
	return new Map((items ?? []).map(item => [item.id, item]));
}

export const PlayerStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withComputed((store, gameStore = inject(GameStore)) => {

		const derivedStatsInstances = computed(() => toMap(store.derivedStatInstances()));

		const derivedStatCodeToId = computed(() =>
			new Map(gameStore.derivedStats().map(stat => [stat.code, stat.id]))
		);

		const getDerivedStatValue = (code: DerivedStatEnum): number => {
			const id = derivedStatCodeToId().get(code);
			if (id === undefined) return 0;
			const instance = derivedStatsInstances().get(id);
			return instance?.value ?? 0;
		};

		return {
			hasCharacter: computed(() => store.player() !== null),
			canMove: computed(() => true),
			playerPosition: computed(() => ({
				x: store.player()?.currentX ?? 0,
				y: store.player()?.currentY ?? 0
			})),
			skillsInstances: computed(() => toMap(store.skillInstances())),
			specialsInstances: computed(() => toMap(store.specialInstances())),
			conditionsInstances: computed(() => toMap(store.conditionInstances())),
			derivedStatsInstances,
			actionPoints: computed(() => getDerivedStatValue(DerivedStatEnum.ACTION_POINTS)),
			carryWeight: computed(() => getDerivedStatValue(DerivedStatEnum.CARRY_WEIGHT)),
			hitPoints: computed(() => getDerivedStatValue(DerivedStatEnum.HIT_POINTS)),
			armorClass: computed(() => getDerivedStatValue(DerivedStatEnum.ARMOR_CLASS)),
			selectedItem: computed(() => store.characterSheet()?.selectedItem)
		};
	}),
	withMethods((
		store,
		gameRepository = inject(GameRepository)
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
					gameRepository.getCurrentCharacter().pipe(
						tap({
							next: (updatedPlayer) => {
								console.log('🗺️ Player loaded');
								patchState(store, {
									player: updatedPlayer,
									isInitialized: true
								})
							},
							error: () => {
								console.error('❌ Error during player loading:');
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
			                  conditionInstances,
			                  currentStats,
			                  characterSheet,
			                  skillInstances,
			                  perkInstances,
			                  specialInstances,
			                  derivedStatInstances
		                  }: Partial<PlayerState>) {

			patchState(store, {
				...(currentStats && { currentStats: { ...currentStats } }),
				...(characterSheet && { characterSheet: { ...characterSheet } }),
				...(skillInstances && { skillInstances: [...skillInstances] }),
				...(perkInstances && { perkInstances: [...perkInstances] }),
				...(specialInstances && { specialInstances: [...specialInstances] }),
				...(derivedStatInstances && { derivedStatInstances: [...derivedStatInstances] }),
				...(conditionInstances && { conditionInstances: [...conditionInstances] })
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

