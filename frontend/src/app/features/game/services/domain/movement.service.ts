import { inject, Injectable } from '@angular/core';
import { NotificationService } from '@core/services/notification.service';
import { Position } from '@features/game/models/position.model';
import { Tile } from '@features/game/models/tile.model';
import { PhaserService } from '@features/game/services/domain/phaser.service';
import { CharacterStore } from '@features/game/stores/character.store';
import { MapStore } from '@features/game/stores/map.store';
import { TranslateService } from '@ngx-translate/core';

/** Service responsable des déplacements, gère les contrôles et la logique de mouvement */
@Injectable({
  providedIn: 'root'
})
export class MovementService {
  private readonly mapStore = inject(MapStore);
  private readonly phaserService = inject(PhaserService);
  private readonly characterStore = inject(CharacterStore);
  private readonly notificationService = inject(NotificationService);
  private readonly translate = inject(TranslateService);

  /** Configure les contrôles clavier */
  public setupKeyboardControls() {
    if (!this.phaserService) return;

    // Touches de direction
    const keyboard = this.phaserService.getKeyboard();
    keyboard.on('keydown-LEFT', () => this.tryMove(-1, 0));
    keyboard.on('keydown-RIGHT', () => this.tryMove(1, 0));
    keyboard.on('keydown-UP', () => this.tryMove(0, -1));
    keyboard.on('keydown-DOWN', () => this.tryMove(0, 1));
  }

  /**
   * Tente un déplacement dans la direction spécifiée
   * @param dx - Déplacement en X
   * @param dy - Déplacement en Y
   */
  private tryMove(dx: number, dy: number) {

    const newPosition = this.newMove(dx, dy);

    if (newPosition) {
      this.mapStore.handleMovement(newPosition);
    }
  }

  /**
   * Vérification de la possibilité de déplacement, avec la position retournée si elle est possible.
   * @param dx
   * @param dy
   * @private
   */
  private newMove(dx: number, dy: number): Position | undefined {

    if (this.mapStore.isMoving()) {
      this.notificationService.showNotification("warning", 'game.movement.errors.alreadyMoving');
      return;
    }

    if (!this.characterStore.canMove()) {
      this.notificationService.showNotification('warning', 'game.movement.errors.noActionPoints');
      return;
    }

    const newPosition = {
      x: this.characterStore.playerPosition().x + dx,
      y: this.characterStore.playerPosition().y + dy
    };

    const targetTile = this.mapStore.tiles().find((tile: Tile) =>
      tile.position.x === newPosition.x &&
      tile.position.y === newPosition.y
    );

    if (!targetTile) {
      console.log('❌ Case inaccessible');
      this.notificationService.showNotification('warning', 'game.terrain.notWalkable');
      return;
    }

    // Vérifier si la tuile est praticable
    const terrainConfig = this.mapStore.terrainConfigMap()[targetTile.type.toLowerCase()];
    if (!terrainConfig?.walkable) {
      const description = terrainConfig.descriptions[this.translate.currentLang];

      const message = `${ this.translate.instant('game.terrain.notWalkable') }: ${ description }`;
      this.notificationService.showNotification('warning', message, {}, true);
      return;

    }

    return newPosition;
  }
}
