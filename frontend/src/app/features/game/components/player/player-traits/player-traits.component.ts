import { Component } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader } from '@angular/material/card';

@Component({
	selector: 'app-player-traits',
	templateUrl: './player-traits.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatCardHeader
	],
	styleUrls: ['./player-traits.component.scss']
})
export class PlayerTraitsComponent {
}
