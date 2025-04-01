import { computed } from '@angular/core';
import { patchState, signalStore, withComputed, withMethods, withState } from '@ngrx/signals';
import { Board } from 'phaser3-rex-plugins/plugins/board-plugin';

// Permet de passer le store dans un constructeur
// https://github.com/ngrx/platform/discussions/4140
export type PhaserStore = InstanceType<typeof PhaserStore>;

type PhaserState = {
	scene: Phaser.Scene | null;
	board: Board | null;
};

export const initialState: PhaserState = {
	scene: null,
	board: null
};

export const PhaserStore = signalStore(
	{ providedIn: 'root' },
	withState(initialState),
	withComputed((store) => ({
		isInitialized: computed(() => store.scene() !== null && store.board() !== null),
		keyboard: computed(() => store.scene()?.input.keyboard),
		existingScene: computed(() => store.scene()!)
	})),
	withMethods((
		store
	) => ({
		setGame(scene: Phaser.Scene, board: Board): void {
			patchState(store, { scene, board });
		}
	}))
);
