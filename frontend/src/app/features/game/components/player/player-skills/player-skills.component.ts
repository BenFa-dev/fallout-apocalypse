import { Component, computed, inject, signal, Signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconButton } from '@angular/material/button';
import { MatCard, MatCardContent, MatCardFooter, MatCardHeader } from "@angular/material/card";
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { MatIcon } from '@angular/material/icon';
import { LanguageService } from '@core/services/language.service';
import { BaseNamedEntity, BaseNamedTaggedInstance } from '@features/game/models/common/base-named.model';
import { GameStore } from '@features/game/stores/game.store';
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslatePipe } from '@ngx-translate/core';

@Component({
	selector: 'app-player-skills',
	templateUrl: './player-skills.component.html',
	imports: [
		MatCard,
		MatCardContent,
		MatCardHeader,
		TranslatePipe,
		MatCardFooter,
		MatGridList,
		MatGridTile,
		MatIcon,
		MatIconButton,
		FormsModule,
		ReactiveFormsModule
	],
	styleUrls: ['./player-skills.component.scss']
})
export class PlayerSkillsComponent {

	private readonly languageService = inject(LanguageService);
	private readonly playerStore = inject(PlayerStore);
	private readonly gameStore = inject(GameStore);

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	protected readonly skills: Signal<BaseNamedEntity[]> = this.gameStore.skills;
	protected readonly skillsInstances: Signal<Map<number, BaseNamedTaggedInstance>> = this.playerStore.skillsInstances;

	protected readonly selectedSkill = signal<BaseNamedTaggedInstance | undefined>(undefined);

	constructor() {
		const initialSkill = this.skillsInstances().get(1);
		if (initialSkill) {
			this.selectedSkill.set(initialSkill);
		}
	}

	protected selectSkill(skillInstance: BaseNamedTaggedInstance, skill: BaseNamedEntity): void {
		this.selectedSkill.set(skillInstance);
		this.playerStore.updateSelectItem(skill);
	}

	protected readonly isSelected = (skill: BaseNamedEntity) =>
		this.selectedSkill()?.id === skill.id;

}
