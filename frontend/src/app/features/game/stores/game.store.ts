import { inject } from '@angular/core';
import { Condition } from '@features/game/models/condition.model';
import { DamageType } from '@features/game/models/damage-type.model';
import { DerivedStat } from '@features/game/models/derived-stat.model';
import { Perk } from '@features/game/models/perk.model';
import { Skill } from '@features/game/models/skill.model';
import { Special } from '@features/game/models/special.model';
import { ConditionRepository } from '@features/game/services/repository/condition.repository';
import { DamageTypeRepository } from '@features/game/services/repository/damage-type.repository';
import { DerivedStatRepository } from '@features/game/services/repository/derived-stat.repository';
import { PerkRepository } from '@features/game/services/repository/perk.repository';
import { SkillRepository } from '@features/game/services/repository/skill.repository';
import { SpecialRepository } from '@features/game/services/repository/special.repository';
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, pipe, switchMap, tap } from 'rxjs';

type GameState = {
	conditions: Condition[];
	damageTypes: DamageType[];
	derivedStats: DerivedStat[];
	perks: Perk[];
	skills: Skill[];
	specials: Special[];
};

const initialState: GameState = {
	conditions: [],
	damageTypes: [],
	derivedStats: [],
	perks: [],
	skills: [],
	specials: []
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
		damageTypeRepository = inject(DamageTypeRepository),
		conditionRepository = inject(ConditionRepository)
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
		),

		loadConditions: rxMethod<void>(
			pipe(
				debounceTime(300),
				distinctUntilChanged(),
				switchMap(() =>
					conditionRepository.getAll().pipe(
						tap({
							next: (conditions) => {
								console.log('🗺️ Conditions loaded');
								patchState(store, { conditions })
							},
							error: () => {
								console.error('❌ Error during conditions loading:');
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
			store.loadConditions();
		}
	})
);
