import { inject, Injectable } from '@angular/core';
import { Position } from '@features/game/models/position.model';
import { PhaserService } from '@features/game/services/domain/phaser.service';
import { CharacterStore } from '@features/game/stores/character.store';
import { PhaserStore } from '@features/game/stores/phaser.store';
import { UntilDestroy } from '@ngneat/until-destroy';
import * as Phaser from 'phaser';

/** Service responsable du joueur, de son affichage et de ses déplacements */
@UntilDestroy()
@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private playerContainer!: Phaser.GameObjects.Container;
  private player!: Phaser.GameObjects.Sprite;
  private playerNameText!: Phaser.GameObjects.Text;

  private readonly characterStore = inject(CharacterStore);
  private readonly phaserService = inject(PhaserService);
  private readonly phaserStore = inject(PhaserStore);

  private readonly textStyle = {
    fontSize: '14px',
    color: '#2ed12e',
    stroke: '#000000',
    strokeThickness: 2
  };

  /** Crée le joueur et ses composants visuels */
  public createPlayer() {
    const scene = this.phaserStore.existingScene();
    const position = this.characterStore.playerPosition();
    const worldXY = this.phaserService.getWorldPosition(position);

    if (!worldXY) return;

    // Création du container à la position initiale du joueur
    this.playerContainer = scene.add.container(worldXY.x, worldXY.y);

    // Création du sprite du joueur
    this.player = scene.add.sprite(0, 0, 'player');

    this.player.setDisplaySize(48, 48);

    // Ajout du sprite au container
    this.playerContainer.add(this.player);

    // Configuration de la caméra pour suivre le container
    scene.cameras.main.startFollow(this.playerContainer);

    // Configuration du nom du joueur
    this.setupPlayerName();

  }

  /** Configure le nom du joueur au-dessus du sprite */
  private setupPlayerName() {
    // Création et positionnement du texte avec le nom depuis le store
    this.playerNameText = this.phaserStore.existingScene().add.text(0, -40, this.characterStore.playerName(), this.textStyle);
    this.playerNameText.setOrigin(0.5);
    this.playerNameText.setDepth(100); // S'assure que le texte est toujours au-dessus
    this.playerContainer.add(this.playerNameText);
  }

  /** Déplace le joueur vers une nouvelle position avec une animation
   * @param newPosition Position cible dans la grille
   */
  moveToPosition(newPosition: Position) {
    const worldXY = this.phaserService.getWorldPosition(newPosition);
    if (worldXY) {
      // Déplace le container (qui contient le joueur et son nom)
      this.phaserStore.existingScene().add.tween({
        targets: this.playerContainer,
        x: worldXY.x,
        y: worldXY.y,
        duration: 150,
        ease: 'Linear'
      });
    }
  }
}
