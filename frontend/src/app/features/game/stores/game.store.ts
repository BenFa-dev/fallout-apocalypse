import { inject } from '@angular/core';
import { Perk } from '@features/game/models/perk.model';
import { Skill } from '@features/game/models/skill.model';
import { Special } from '@features/game/models/special.model';
import { PerkService } from '@features/game/services/api/perk.service';
import { SkillService } from '@features/game/services/api/skill.service';
import { SpecialService } from '@features/game/services/api/special.service';
import { patchState, signalStore, withHooks, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, pipe, switchMap, tap } from 'rxjs';

type GameState = {
	skills: Skill[];
	perks: Perk[];
	specials: Special[];
};

const initialState: GameState = {
	skills: [],
	perks: [],
	specials: []
};

export const GameStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withMethods((
		store,
		skillService = inject(SkillService),
		perkService = inject(PerkService),
		specialService = inject(SpecialService)
	) => ({
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
		)
	})),
	withHooks({
		onInit(store) {
			store.loadSkills();
			store.loadSpecials();
			store.loadPerks();
		}
	})
);
