import { inject, Injectable } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { InventoryComponent } from '@features/game/components/inventory/inventory.component';
import { PlayerComponent } from '@features/game/components/player/player.component';
import { InventoryStore } from '@features/game/stores/inventory.store';
import { PhaserStore } from '@features/game/stores/phaser.store';
import { PlayerStore } from '@features/game/stores/player.store';

/** Service responsable de la gestion des entrées clavier et des interactions */
@Injectable({
	providedIn: 'root'
})
export class InputService {
	private readonly inventoryStore = inject(InventoryStore);
	private readonly phaserStore = inject(PhaserStore);
	private readonly playerStore = inject(PlayerStore);

	private readonly dialog = inject(MatDialog);

	private inventoryDialogRef: MatDialogRef<InventoryComponent> | null = null;
	private playerDialogRef: MatDialogRef<PlayerComponent> | null = null;

	/** Configure les contrôles clavier */
	public setupKeyboardControls() {
		const keyboard = this.phaserStore.keyboard();
		if (!keyboard) return;

		// Touche I pour l'inventaire
		keyboard.on('keydown-I', () => this.openInventory());

		// Touche C pour al ficher de personnage
		keyboard.on('keydown-C', () => this.openCharacterSheet());
	}

	private openInventory() {
		if (this.inventoryDialogRef) return;

		// Pause du jeu en ouvrant l'inventaire
		this.phaserStore.scene()?.scene.pause();
		this.inventoryDialogRef = this.dialog.open(InventoryComponent, {
			height: 'calc(100vh - 64px - 32px - 64px)',
			disableClose: true,
			autoFocus: false,
			restoreFocus: false
		});

		this.inventoryDialogRef.afterClosed().subscribe({
			next: () => {
				this.inventoryDialogRef = null;
				this.inventoryStore.close();
				// Reprise du jeu en fermant
				this.phaserStore.scene()?.scene.resume();
			},
			error: (error) => {
				console.error('Erreur lors de la fermeture de l\'inventaire:', error);
				this.inventoryDialogRef = null;
				this.phaserStore.scene()?.scene.resume();
			}
		});
	}

	private openCharacterSheet() {
		if (this.playerDialogRef) return;

		// Pause du jeu en ouvrant la fiche de perso
		this.phaserStore.scene()?.scene.pause();
		this.playerDialogRef = this.dialog.open(PlayerComponent, {
			height: 'calc(100vh - 64px - 32px - 64px)',
			disableClose: true,
			autoFocus: false,
			restoreFocus: false,
			maxWidth: 'none'
		});

		this.playerDialogRef.afterClosed().subscribe({
			next: () => {
				this.playerDialogRef = null;
				this.playerStore.close();
				// Reprise du jeu en fermant
				this.phaserStore.scene()?.scene.resume();
			},
			error: (error) => {
				console.error('Erreur lors de la fermeture de la fiche de personnage:', error);
				this.playerDialogRef = null;
				this.phaserStore.scene()?.scene.resume();
			}
		});
	}
}
