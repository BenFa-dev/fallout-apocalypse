import { Component, computed, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent } from "@angular/material/card";
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { LanguageService } from '@core/services/language.service';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { Condition, ConditionInstance } from '@features/game/models/condition.model';
import { GameStore } from '@features/game/stores/game.store';
import { PlayerStore } from '@features/game/stores/player.store';

@Component({
	selector: 'app-player-stats',
	templateUrl: './player-stats.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatGridList,
		MatGridTile
	],
	styleUrls: ['./player-stats.component.scss']
})
export class PlayerStatsComponent {
	private readonly languageService = inject(LanguageService);

	private readonly playerStore = inject(PlayerStore);
	private readonly gameStore = inject(GameStore);

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	protected readonly conditions: Signal<Condition[]> = this.gameStore.conditions;
	protected readonly conditionsInstances: Signal<Map<number, ConditionInstance>> = this.playerStore.conditionsInstances;

	protected selectCondition(condition: BaseNamedEntity): void {
		console.log(condition);
		this.playerStore.updateSelectItem(condition);
	}

}
