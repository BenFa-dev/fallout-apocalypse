import { Component, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent } from "@angular/material/card";
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { CharacterStatus } from '@features/game/models/character.model';
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
	selector: 'app-player-stats',
	templateUrl: './player-stats.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatGridList,
		MatGridTile,
		TranslatePipe
	],
	styleUrls: ['./player-stats.component.scss']
})
export class PlayerStatsComponent {
	private readonly playerStore = inject(PlayerStore);

	status: Signal<CharacterStatus | null | undefined> = this.playerStore.status;
}
