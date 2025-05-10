import { Component, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent } from "@angular/material/card";
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { CharacterStats } from '@features/game/models/character.model';
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
	selector: 'app-player-additional-stats',
	templateUrl: './player-additional-stats.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatGridTile,
		TranslatePipe,
		MatGridList
	],
	styleUrls: ['./player-additional-stats.component.scss']
})
export class PlayerAdditionalStatsComponent {
	private readonly playerStore = inject(PlayerStore);

	stats: Signal<CharacterStats | null> = this.playerStore.stats;
}
