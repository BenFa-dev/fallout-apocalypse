import { CommonModule } from '@angular/common';
import { Component, inject, Signal } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatCard, MatCardContent } from '@angular/material/card';
import { MatDivider } from '@angular/material/divider';
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { MatIcon } from '@angular/material/icon';
import { MatTooltip } from '@angular/material/tooltip';
import { Character } from '@features/game/models/character.model';
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

	special: Signal<{ key: string; value: any; }[]> = this.playerStore.special;
	player: Signal<Character | null> = this.playerStore.player;

}

