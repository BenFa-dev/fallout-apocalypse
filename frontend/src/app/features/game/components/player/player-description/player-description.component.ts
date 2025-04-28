import { Component } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader } from '@angular/material/card';

@Component({
	selector: 'app-player-description',
	templateUrl: './player-description.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatCardHeader
	],
	styleUrls: ['./player-description.component.scss']
})
export class PlayerDescriptionComponent {
}
