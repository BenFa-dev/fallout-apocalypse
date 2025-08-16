import { Component, computed, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent } from "@angular/material/card";
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { LanguageService } from '@core/services/language.service';
import { CharacterCurrentStats } from '@features/game/models/character.model';
import { BaseNamedEntity, BaseNamedIntegerInstance } from '@features/game/models/common/base-named.model';
import { DerivedStatEnum } from '@features/game/models/derived-stat.model';
import { GameStore } from '@features/game/stores/game.store';
import { PlayerStore } from '@features/game/stores/player.store';

@Component({
	selector: 'app-player-derived-stats',
	templateUrl: './player-derived-stats.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatGridTile,
		MatGridList
	],
	styleUrls: ['./player-derived-stats.component.scss']
})
export class PlayerDerivedStatsComponent {
	private readonly playerStore = inject(PlayerStore);
	private readonly gameStore = inject(GameStore);
	private readonly languageService = inject(LanguageService);

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());
	protected readonly derivedStats: Signal<BaseNamedEntity[] | null> = this.gameStore.derivedStats;
	protected readonly derivedStatInstances: Signal<Map<number, BaseNamedIntegerInstance>> = this.playerStore.derivedStatsInstances;
	protected readonly currentStats: Signal<CharacterCurrentStats | null> = this.playerStore.currentStats;

	onSpecialClicked(derivedStat: BaseNamedEntity) {
		this.playerStore.updateSelectItem(derivedStat);
	}

	protected readonly DerivedStatEnum = DerivedStatEnum;
}
