import { inject } from '@angular/core';
import { DerivedStat } from '@features/game/models/derived-stat.model';
import { DamageType } from '@features/game/models/inventory/inventory.model';
import { Perk } from '@features/game/models/perk.model';
import { Skill } from '@features/game/models/skill.model';
import { Special } from '@features/game/models/special.model';
import { DamageTypeRepository } from '@features/game/services/repository/damage-type.repository';
import { DerivedStatRepository } from '@features/game/services/repository/derived-stat.repository';
import { PerkRepository } from '@features/game/services/repository/perk.repository';
import { SkillRepository } from '@features/game/services/repository/skill.repository';
import { SpecialRepository } from '@features/game/services/repository/special.repository';
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, pipe, switchMap, tap } from 'rxjs';

type GameState = {
	skills: Skill[];
	perks: Perk[];
	specials: Special[];
	derivedStats: DerivedStat[];
	damageTypes: DamageType[];
};

const initialState: GameState = {
	skills: [],
	perks: [],
	specials: [],
	derivedStats: [],
	damageTypes: []
};

export const GameStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withMethods((
		store,
		skillRepository = inject(SkillRepository),
		perkRepository = inject(PerkRepository),
		specialRepository = inject(SpecialRepository),
		derivedStatRepository = inject(DerivedStatRepository),
		damageTypeRepository = inject(DamageTypeRepository)
	) => ({
		loadSkills: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				switchMap(() =>
					skillRepository.getAll().pipe(
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
					specialRepository.getAll().pipe(
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
					perkRepository.getAllAvailable().pipe(
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

		loadDerivedStats: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				switchMap(() =>
					derivedStatRepository.getAll().pipe(
						tap({
							next: (derivedStats) => {
								console.log('🗺️ Stats dérivées chargées');
								patchState(store, { derivedStats })
							},
							error: () => {
								console.error('❌ Erreur lors du chargement des stats dérivées:');
							}
						})
					)
				)
			)
		),

		loadDamageTypes: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				switchMap(() =>
					damageTypeRepository.getAll().pipe(
						tap({
							next: (damageTypes) => {
								console.log('🗺️ Damage types loaded');
								patchState(store, { damageTypes })
							},
							error: () => {
								console.error('❌ Error during damage types loading:');
							}
						})
					)
				)
			)
		)
	})),
	withHooks({
		onInit(store) {
			store.loadSkills();
			store.loadSpecials();
			store.loadPerks();
			store.loadDerivedStats();
			store.loadDamageTypes();
		}
	})
);
