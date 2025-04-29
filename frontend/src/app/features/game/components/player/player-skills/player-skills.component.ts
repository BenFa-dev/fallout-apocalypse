import { Component, computed, inject, signal, Signal } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatIconButton } from '@angular/material/button';
import { MatCard, MatCardContent, MatCardFooter, MatCardHeader } from "@angular/material/card";
import { MatGridList, MatGridTile } from '@angular/material/grid-list';
import { MatIcon } from '@angular/material/icon';
import { LanguageService } from '@core/services/language.service';
import { Skill, SkillInstance } from '@features/game/models/skill.model';
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

	protected readonly currentLanguage = computed(() => this.languageService.currentLanguage());

	protected readonly skills: Signal<Skill[]> = this.playerStore.skills;
	protected readonly skillsInstances: Signal<Map<number, SkillInstance>> = this.playerStore.skillsInstances;

	protected readonly selectedSkill = signal<SkillInstance | undefined>(undefined);

	constructor() {
		const initialSkill = this.skillsInstances().get(1);
		if (initialSkill) {
			this.selectedSkill.set(initialSkill);
		}
	}

	protected selectSkill(skillInstance: SkillInstance, skill: Skill): void {
		this.selectedSkill.set(skillInstance);
		this.playerStore.updateSelectItem(skill);
	}

	protected readonly isSelected = (skill: Skill) =>
		this.selectedSkill()?.skillId === skill.id;

}
