import { Component } from '@angular/core';
import { MatCard, MatCardContent, MatCardHeader } from "@angular/material/card";
import { TranslatePipe } from '@ngx-translate/core';

@Component({
	selector: 'app-player-skills',
	templateUrl: './player-skills.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatCardHeader,
		TranslatePipe
	],
	styleUrls: ['./player-skills.component.scss']
})
export class PlayerSkillsComponent {
}
