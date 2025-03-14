import { inject } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivateFn, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthGuardData, createAuthGuard } from 'keycloak-angular';

const isAccessAllowed = async (
	_route: ActivatedRouteSnapshot,
	state: RouterStateSnapshot,
	authData: AuthGuardData
): Promise<boolean | UrlTree> => {
	const {authenticated} = authData;
	const router = inject(Router);

	if (!authenticated) {
		return router.createUrlTree(['/'], {
			queryParams: {
				returnUrl: state.url,
				authRequired: true
			}
		});
	}

	return true;
};

export const authGuard: CanActivateFn = createAuthGuard(isAccessAllowed);
