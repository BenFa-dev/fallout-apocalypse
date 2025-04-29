import { NgOptimizedImage } from '@angular/common';
import { Component, computed, inject, Signal } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader } from '@angular/material/card';
import { LanguageService } from '@core/services/language.service';
import { BaseNamedEntity } from '@features/game/models/common/base-named.model';
import { PlayerStore } from '@features/game/stores/player.store';

@Component({
	selector: 'app-player-description',
	templateUrl: './player-description.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatCardHeader,
		NgOptimizedImage
	],
	styleUrls: ['./player-description.component.scss']
})
export class PlayerDescriptionComponent {
	private readonly playerStore = inject(PlayerStore);
	private readonly languageService = inject(LanguageService);

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	protected readonly selectedItem: Signal<BaseNamedEntity | undefined> = this.playerStore.selectedItem;
}
