import { Component } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader } from "@angular/material/card";

@Component({
	selector: 'app-player-additional-stats',
	templateUrl: './player-additional-stats.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatCardHeader
	],
	styleUrls: ['./player-additional-stats.component.scss']
})
export class PlayerAdditionalStatsComponent {

}
