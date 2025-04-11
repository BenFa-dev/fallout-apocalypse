import { inject, Injectable } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { InventoryComponent } from '@features/game/components/inventory/inventory.component';
import { InventoryStore } from '@features/game/stores/inventory.store';
import { PhaserStore } from '@features/game/stores/phaser.store';

/** Service responsable de la gestion des entrées clavier et des interactions */
@Injectable({
	providedIn: 'root'
})
export class InputService {
	private readonly inventoryStore = inject(InventoryStore);
	private readonly phaserStore = inject(PhaserStore);

	private readonly dialog = inject(MatDialog);

	private dialogRef: MatDialogRef<InventoryComponent> | null = null;

	/** Configure les contrôles clavier */
	public setupKeyboardControls() {
		const keyboard = this.phaserStore.keyboard();
		if (!keyboard) return;

		// Touche I pour l'inventaire
		keyboard.on('keydown-I', () => this.openInventory());
	}

	private openInventory() {
		if (this.dialogRef) return;

		// Pause du jeu en ouvrant l'inventaire
		this.phaserStore.scene()?.scene.pause();
		this.dialogRef = this.dialog.open(InventoryComponent, {
			height: 'calc(100vh - 64px - 32px - 64px)',
			disableClose: true,
			autoFocus: false,
			restoreFocus: false
		});

		this.dialogRef.afterClosed().subscribe({
			next: () => {
				this.dialogRef = null;
				this.inventoryStore.close();
				// Reprise du jeu en fermant
				this.phaserStore.scene()?.scene.resume();
			},
			error: (error) => {
				console.error('Erreur lors de la fermeture de l\'inventaire:', error);
				this.dialogRef = null;
				this.phaserStore.scene()?.scene.resume();
			}
		});
	}
}
