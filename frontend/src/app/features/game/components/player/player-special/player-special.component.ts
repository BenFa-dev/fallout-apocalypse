import { CommonModule } from '@angular/common';
import { Component, computed, inject, Signal } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { LanguageService } from '@core/services/language.service';
import { Character, CharacterCurrentStats } from '@features/game/models/character.model';
import { BaseNamedEntity, BaseNamedIntegerInstance } from '@features/game/models/common/base-named.model';
import { GameStore } from '@features/game/stores/game.store';
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
	selector: 'app-player-special',
	imports: [CommonModule, TranslatePipe, MatGridTile, MatTooltip, MatCard, MatCardContent, MatGridList, MatIcon, MatIconButton, MatDivider
	],
	templateUrl: './player-special.component.html',
	styleUrls: ['./player-special.component.scss']
})
export class PlayerSpecialComponent {
	isCreation = false;

	private readonly playerStore = inject(PlayerStore);
	private readonly gameStore = inject(GameStore);
	private readonly languageService = inject(LanguageService);

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	protected readonly specialsInstances: Signal<Map<number, BaseNamedIntegerInstance>> = this.playerStore.specialsInstances;
	protected readonly specials: Signal<BaseNamedEntity[]> = this.gameStore.specials;
	protected readonly currentStats: Signal<CharacterCurrentStats | null> = this.playerStore.currentStats;
	protected readonly player: Signal<Character | null> = this.playerStore.player;

	onSpecialClicked(special: BaseNamedEntity) {
		this.playerStore.updateSelectItem(special);
	}
}

