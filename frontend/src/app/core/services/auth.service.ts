import { computed, effect, inject, Injectable, signal } from '@angular/core';
import { User } from '@core/models/user.model';
import type { KeycloakProfile } from 'keycloak-js';
import Keycloak from 'keycloak-js';
import { KEYCLOAK_EVENT_SIGNAL, KeycloakEventType, ReadyArgs, typeEventArgs } from 'keycloak-angular';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  readonly #currentUser = signal<User | null>(null);
  readonly #isAuthenticated = signal<boolean>(false);
  private readonly keycloak = inject(Keycloak);
  private readonly keycloakEvent = inject(KEYCLOAK_EVENT_SIGNAL);

  readonly currentUser = computed(() => this.#currentUser());
  readonly isAuthenticated = computed(() => this.#isAuthenticated());

  constructor() {
    effect(() => {
      const event = this.keycloakEvent();

      if (event.type === KeycloakEventType.Ready) {
        const isAuth = typeEventArgs<ReadyArgs>(event.args);
        this.#isAuthenticated.set(isAuth);
        if (isAuth) {
          this.keycloak.loadUserProfile()
            .then((profile: KeycloakProfile) => {
              const roles = this.keycloak.resourceAccess?.[this.keycloak.clientId ?? '']?.roles ?? [];
              this.#currentUser.set({
                id: profile.id ?? '',
                name: profile.username ?? '',
                email: profile.email ?? '',
                roles,
                createdAt: new Date(),
                lastLogin: new Date()
              });
            })
            .catch(() => this.#currentUser.set(null));
        }
      }

      if (event.type === KeycloakEventType.AuthLogout) {
        this.#isAuthenticated.set(false);
        this.#currentUser.set(null);
      }
    });
  }

  login(redirectUri = window.location.origin): void {
    this.keycloak.login({
      redirectUri,
      prompt: 'login'
    });
  }

  logout(redirectUri = window.location.origin): void {
    this.keycloak.logout({redirectUri});
  }

  hasRole(role: string): boolean {
    return this.currentUser()?.roles?.includes(role) ?? false;
  }
}

