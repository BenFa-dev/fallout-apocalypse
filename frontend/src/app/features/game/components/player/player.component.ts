import { Component, HostListener, inject } from '@angular/core';
import { MatIconButton } from '@angular/material/button';
import { MatDialogRef } from '@angular/material/dialog';
import { MatIcon } from '@angular/material/icon';
import { MatToolbar } from '@angular/material/toolbar';
import { MatTooltip } from '@angular/material/tooltip';
import { PlayerSkillsComponent } from '@features/game/components/player/player-skills/player-skills.component';
import { PlayerSpecialComponent } from '@features/game/components/player/player-special/player-special.component';
import { PlayerStatsComponent } from '@features/game/components/player/player-stats/player-stats.component';
import { PlayerTraitsComponent } from '@features/game/components/player/player-traits/player-traits.component';
import { TranslatePipe } from '@ngx-translate/core';
import { PlayerSummaryComponent } from './player-summary/player-summary.component';

@Component({
	selector: 'app-player',
	imports: [
		PlayerSummaryComponent,
		MatIcon,
		MatIconButton,
		MatToolbar,
		TranslatePipe,
		MatTooltip,
		PlayerSpecialComponent,
		PlayerStatsComponent,
		PlayerSkillsComponent,
		PlayerTraitsComponent
	],
	templateUrl: './player.component.html',
	styleUrls: ['./player.component.scss']
})
export class PlayerComponent {
	private readonly dialogRef = inject(MatDialogRef<PlayerComponent>);

	@HostListener('window:keydown', ['$event'])
	handleKeyboardEvent(event: KeyboardEvent) {
		if ((event.key === 'c' || event.key === 'C') && !['INPUT', 'TEXTAREA'].includes((event.target as HTMLElement).tagName)) {
			event.preventDefault();
			this.close();
		}
	}

	close() {
		this.dialogRef.close();
	}
}
