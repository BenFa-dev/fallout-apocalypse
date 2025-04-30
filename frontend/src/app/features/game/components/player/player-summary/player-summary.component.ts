import { Component, inject } from '@angular/core';
import { PlayerStore } from '@features/game/stores/player.store';

@Component({
	selector: 'app-player-summary',
	templateUrl: './player-summary.component.html',
	styleUrls: ['./player-summary.component.scss']
})
export class PlayerSummaryComponent {
	private readonly playerStore = inject(PlayerStore);
	player = this.playerStore.player;
}
