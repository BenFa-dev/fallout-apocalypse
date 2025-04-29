import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatButton, MatIconButton } from '@angular/material/button';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatInputModule } from '@angular/material/input';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { GameService } from '@features/game/services/api/game.service';
import { PlayerStore } from '@features/game/stores/player.store';
import { TranslateModule, TranslateService } from '@ngx-translate/core';

@Component({
	selector: 'app-character-creation',
	imports: [
		ReactiveFormsModule,
		TranslateModule,
		MatCardModule,
		MatInputModule,
		MatIconModule,
		MatIconButton,
		MatButton
	],
	templateUrl: './character-creation.component.html',
	styleUrls: ['./character-creation.component.scss']
})
export class CharacterCreationComponent implements OnInit {
	characterForm!: FormGroup;
	remainingPoints = 5;
	totalPoints = 40;
	specialStats = [
		{ key: 'strength', label: 'character.stats.strength' },
		{ key: 'perception', label: 'character.stats.perception' },
		{ key: 'endurance', label: 'character.stats.endurance' },
		{ key: 'charisma', label: 'character.stats.charisma' },
		{ key: 'intelligence', label: 'character.stats.intelligence' },
		{ key: 'agility', label: 'character.stats.agility' },
		{ key: 'luck', label: 'character.stats.luck' }
	];

	private gameService = inject(GameService);
	private playerStore = inject(PlayerStore);
	private fb = inject(FormBuilder);
	private router = inject(Router);
	private translate = inject(TranslateService);
	private snackBar = inject(MatSnackBar);

	constructor() {
		this.initForm();
	}

	private initForm() {
		this.characterForm = this.fb.group({
			name: ['', [Validators.required, Validators.minLength(3)]],
			special: this.fb.group({
				strength: [5],
				perception: [5],
				endurance: [5],
				charisma: [5],
				intelligence: [5],
				agility: [5],
				luck: [5]
			})
		});

		this.characterForm.get('special')?.valueChanges.subscribe(() => {
			this.calculateRemainingPoints();
		});
	}

	ngOnInit() {
		this.calculateRemainingPoints();
	}

	adjustStat(stat: string, change: number) {
		const control = this.characterForm.get(`special.${ stat }`);
		if (control) {
			const currentValue = control.value;
			const newValue = currentValue + change;

			if (newValue >= 1 && newValue <= 10 && (change < 0 || this.remainingPoints > 0)) {
				control.setValue(newValue);
			}
		}
	}

	calculateRemainingPoints() {
		const special = this.characterForm.get('special')?.value;
		if (special) {
			const usedPoints = (Object.values(special) as number[]).reduce((a, b) => a + b, 0);
			this.remainingPoints = this.totalPoints - usedPoints;
		}
	}

	onSubmit() {
		//TODO Rework
		/*if (this.characterForm.valid && this.remainingPoints === 0) {
			const formValue = this.characterForm.value as { name: string; special: Character['special'] };
			this.gameService.createCharacter(formValue.name, formValue.special).subscribe({
				next: (character: Character) => {
					this.playerStore.updateCharacter(character);
					this.router.navigate(['/game']);
				},
				error: () => {
					this.snackBar.open(
						this.translate.instant('character.creation.error'),
						this.translate.instant('common.close'),
						{ duration: 3000 }
					);
				}
			});
		}*/
	}
}
