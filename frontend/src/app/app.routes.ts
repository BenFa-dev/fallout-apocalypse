import { Routes } from '@angular/router';
import { authGuard } from '@core/guards/auth-guard';
import { characterGuard } from '@core/guards/character-guard';

export const routes: Routes = [
	{
		path: 'game',
		children: [
			{
				path: '',
				loadComponent: () => import('./features/game/components/map/game.component').then(m => m.GameComponent)
			}
		],
		canActivate: [authGuard, characterGuard]
	},
	{
		path: 'character-creation',
		loadComponent: () => import('./features/game/components/character-creation/character-creation.component').then(m => m.CharacterCreationComponent),
		canActivate: [authGuard, characterGuard]
	},
	{
		path: 'landing',
		loadComponent: () => import('./features/landing/components/landing/landing.component').then(m => m.LandingComponent)
	},
	{
		path: '',
		redirectTo: 'landing',
		pathMatch: 'full'
	}
];
