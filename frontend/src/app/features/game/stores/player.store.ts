import { computed, inject } from '@angular/core';
import {
	Character,
	CharacterCurrentStats,
	CharacterSheet,
	CharacterStats
} from '@features/game/models/character.model';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { DerivedStatEnum, DerivedStatInstance } from '@features/game/models/derived-stat.model';
import { PerkInstance } from '@features/game/models/perk.model';
import { SkillInstance } from '@features/game/models/skill.model';
import { SpecialInstance } from '@features/game/models/special.model';
import { Tile } from '@features/game/models/tile.model';
import { GameRepository } from '@features/game/services/repository/game.repository';
import { GameStore } from '@features/game/stores/game.store';
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
	derivedStatInstances: DerivedStatInstance[] | null;

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
	derivedStatInstances: [],
	characterSheet: null,
	stats: null,
	currentStats: null
};

export const PlayerStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withComputed((store, gameStore = inject(GameStore)) => {
		const skillsInstances = computed(() =>
			new Map((store.skillInstances() ?? []).map(skill => [skill.skillId, skill]))
		);

		const specialsInstances = computed(() =>
			new Map((store.specialInstances() ?? []).map(special => [special.specialId, special]))
		);

		const derivedStatsInstances = computed(() =>
			new Map((store.derivedStatInstances() ?? []).map(stat => [stat.derivedStatId, stat]))
		);

		const derivedStatCodeToId = new Map(
			gameStore.derivedStats().map(stat => [stat.code, stat.id])
		);

		const getDerivedStatValue = (code: DerivedStatEnum): number => {
			const id = derivedStatCodeToId.get(code);
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
			skillsInstances,
			specialsInstances,
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
			                  specialInstances,
			                  derivedStatInstances
		                  }: Partial<PlayerState>) {

			patchState(store, {
				...(stats && { stats: { ...stats } }),
				...(currentStats && { currentStats: { ...currentStats } }),
				...(characterSheet && { characterSheet: { ...characterSheet } }),
				...(skillInstances && { skillInstances: [...skillInstances] }),
				...(perkInstances && { perkInstances: [...perkInstances] }),
				...(specialInstances && { specialInstances: [...specialInstances] }),
				...(derivedStatInstances && { derivedStatInstances: [...derivedStatInstances] })
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

