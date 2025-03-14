import { inject, Injectable } from '@angular/core';
import { Position } from '@features/game/models/position.model';
import { CharacterStore } from '@features/game/stores/character.store';
import { UntilDestroy } from '@ngneat/until-destroy';
import * as Phaser from 'phaser';
import { GridService } from './grid.service';

/** Service responsable du joueur, de son affichage et de ses déplacements */
@UntilDestroy()
@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private playerContainer!: Phaser.GameObjects.Container;
  private player!: Phaser.GameObjects.Sprite;
  private playerNameText!: Phaser.GameObjects.Text;
  private scene!: Phaser.Scene;

  private readonly characterStore = inject(CharacterStore);
  private readonly gridService = inject(GridService);

  private readonly textStyle = {
    fontSize: '14px',
    color: '#2ed12e',
    stroke: '#000000',
    strokeThickness: 2
  };

  /** Initialise le joueur avec la scène de jeu
   * @param scene La scène de jeu
   */
  initialize(scene: Phaser.Scene) {
    this.scene = scene;
    this.createPlayer();
  }

  /** Crée le joueur et ses composants visuels */
  private createPlayer() {

    const position = this.characterStore.playerPosition();

    const worldXY = this.gridService.getWorldXY(position.x, position.y);
    if (!worldXY) return;

    // Création du container à la position initiale du joueur
    this.playerContainer = this.scene.add.container(worldXY.x, worldXY.y);

    // Création du sprite du joueur
    this.player = this.scene.add.sprite(0, 0, 'player');
    this.player.setDisplaySize(48, 48);

    // Ajout du sprite au container
    this.playerContainer.add(this.player);

    // Configuration de la caméra pour suivre le container
    this.scene.cameras.main.startFollow(this.playerContainer);

    // Configuration du nom du joueur
    this.setupPlayerName();
  }

  /** Configure le nom du joueur au-dessus du sprite */
  private setupPlayerName() {
    // Création et positionnement du texte avec le nom depuis le store
    this.playerNameText = this.scene.add.text(0, -40, this.characterStore.playerName(), this.textStyle);
    this.playerNameText.setOrigin(0.5);
    this.playerNameText.setDepth(100); // S'assure que le texte est toujours au-dessus
    this.playerContainer.add(this.playerNameText);
  }

  /** Déplace le joueur vers une nouvelle position avec une animation
   * @param newPosition Position cible dans la grille
   */
  moveToPosition(newPosition: Position) {
    const worldXY = this.gridService.getWorldXY(newPosition.x, newPosition.y);

    if (worldXY) {
      // Déplace le container (qui contient le joueur et son nom)
      this.scene.add.tween({
        targets: this.playerContainer,
        x: worldXY.x,
        y: worldXY.y,
        duration: 150,
        ease: 'Linear'
      });
    }
  }

  /** Retourne le container du joueur pour les autres services qui en ont besoin */
  getContainer(): Phaser.GameObjects.Container {
    return this.playerContainer;
  }
}
