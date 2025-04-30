import { inject } from '@angular/core';
import { toObservable } from '@angular/core/rxjs-interop';
import { CanActivateFn, Router } from '@angular/router';
import { PlayerStore } from '@features/game/stores/player.store';
import { filter, map, take } from 'rxjs';

// Vérifie si un personnage existe et redirige si nécessaire
// TODO Vérifier la pratique, init un store directement dans la guard ?
export const characterGuard: CanActivateFn = (route, state) => {
	const store = inject(PlayerStore);
	const router = inject(Router);

	if (!store.isInitialized()) {
		store.loadPlayer();
	}

	return toObservable(store.isInitialized).pipe(
		filter((isInit: boolean) => isInit),
		take(1),
		map(() =>
			store.hasCharacter()
				? state.url === '/game' ? true : router.createUrlTree(['/game'])
				: state.url === '/character-creation' ? true : router.createUrlTree(['/character-creation'])
		)
	);
};
