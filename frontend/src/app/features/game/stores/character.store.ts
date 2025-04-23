import { computed, inject } from '@angular/core';
import { Character } from '@features/game/models/character.model';
import { PlayerStats } from '@features/game/models/player.model';
import { Tile } from '@features/game/models/tile.model';
import { GameService } from '@features/game/services/api/game.service';
import { patchState, signalStore, withComputed, withMethods, withState } from '@ngrx/signals';
import { rxMethod } from '@ngrx/signals/rxjs-interop';
import { debounceTime, distinctUntilChanged, pipe, switchMap, tap } from 'rxjs';

// Permet de passer le store dans un constructeur
// https://github.com/ngrx/platform/discussions/4140
export type CharacterStore = InstanceType<typeof CharacterStore>;

type CharacterState = {
  isInitialized: boolean
  isLoading: boolean,
  playerStats: PlayerStats;
  character: Character | null;
  currentTile: Tile | null;
  currentTilesInVision: Tile[];
  previousTilesInVision: Tile[];
};

const initialState: CharacterState = {
  isInitialized: false,
  isLoading: false,
  playerStats: {
    name: '',
    health: 100,
    actionPoints: 0,
    maxActionPoints: 0
  },
  character: null,
  currentTile: null,
  currentTilesInVision: [],
  previousTilesInVision: []
};

export const CharacterStore = signalStore(
  { providedIn: 'root' },
  withState(initialState),
  withComputed((store) => ({
    hasCharacter: computed(() => store.character() !== null),
    playerName: computed(() => store.playerStats().name),
    canMove: computed(() => store.playerStats().actionPoints > 0),
    playerPosition: computed(() => ({
      x: store.character()?.currentX ?? 0,
      y: store.character()?.currentY ?? 0
    }))
  })),
  withMethods((
    store,
    gameService = inject(GameService)
  ) => ({

    updatePlayerCurrentTile(currentTile: Tile): void {
      patchState(store, { currentTile });
    },

    updatePlayerCurrentVision(currentTilesInVision: Tile[]): void {
      patchState(store, { previousTilesInVision: store.currentTilesInVision(), currentTilesInVision });
    },

    loadCharacter: rxMethod<void>(
      pipe(
        debounceTime(300),
        distinctUntilChanged(),
        tap(() => patchState(store, { isLoading: true })),
        switchMap(() =>
          gameService.getCurrentCharacter().pipe(
            tap({
              next: (updatedCharacter) => {
                console.log('🗺️ Joueur chargé');
                patchState(store, {
                  character: updatedCharacter,
                  playerStats: {
                    name: updatedCharacter?.name ?? '',
                    health: 100,
                    actionPoints: updatedCharacter?.currentActionPoints ?? 0,
                    maxActionPoints: updatedCharacter?.maxActionPoints ?? 0
                  },
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

    updateCharacter(character?: Character) {
      if (!character) return;

      patchState(store, {
        character,
        playerStats: {
          ...store.playerStats(),
          actionPoints: character.currentActionPoints,
          maxActionPoints: character.maxActionPoints,
          name: character.name,
          health: 100
        }
      });
    }
  }))
);

