import { provideHttpClient, withFetch, withInterceptors } from '@angular/common/http';
import { ApplicationConfig, importProvidersFrom, provideZonelessChangeDetection } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { provideNativeDateAdapter } from '@angular/material/core';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatProgressBarModule } from '@angular/material/progress-bar';
import { MatSelectModule } from '@angular/material/select';
import { MatSnackBarModule } from '@angular/material/snack-bar';
import { provideAnimations } from '@angular/platform-browser/animations';
import { provideRouter, withViewTransitions } from '@angular/router';
import { AuthService } from '@core/services/auth.service';
import { environment } from '@environments/environment';
import { provideTranslateService } from '@ngx-translate/core';
import { provideTranslateHttpLoader } from '@ngx-translate/http-loader';
import {
	AutoRefreshTokenService,
	createInterceptorCondition,
	INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
	IncludeBearerTokenCondition,
	includeBearerTokenInterceptor,
	provideKeycloak,
	UserActivityService,
	withAutoRefreshToken
} from 'keycloak-angular';
import { routes } from './app.routes';

const apiCondition = createInterceptorCondition<IncludeBearerTokenCondition>({
	urlPattern: /^\/api\/.*/i,
	bearerPrefix: 'Bearer'
});

export const appConfig: ApplicationConfig = {
	providers: [
		provideZonelessChangeDetection(),
		provideRouter(routes, withViewTransitions()),
		provideHttpClient(
			withFetch(),
			withInterceptors([includeBearerTokenInterceptor])
		),
		provideAnimations(),
		provideNativeDateAdapter(),
		{
			provide: INCLUDE_BEARER_TOKEN_INTERCEPTOR_CONFIG,
			useValue: [apiCondition]
		},
		provideTranslateService({
			lang: 'fr',
			fallbackLang: 'fr',
			loader: provideTranslateHttpLoader({
				prefix: '/assets/i18n/',
				suffix: '.json'
			})
		}),
		importProvidersFrom(
			MatCardModule,
			MatSelectModule,
			MatProgressBarModule,
			MatFormFieldModule,
			MatInputModule,
			MatIconModule,
			MatSnackBarModule
		),
		AuthService,
		provideKeycloak({
			config: {
				url: environment.keycloakConfig.url,
				realm: environment.keycloakConfig.realm,
				clientId: environment.keycloakConfig.clientId
			},
			initOptions: {
				onLoad: 'check-sso',
				silentCheckSsoRedirectUri: window.location.origin + '/assets/silent-check-sso.html',
				checkLoginIframe: false
			},
			features: [
				withAutoRefreshToken({
					onInactivityTimeout: 'logout',
					sessionTimeout: 60000
				})
			],
			providers: [AutoRefreshTokenService, UserActivityService]
		})
	]
};
