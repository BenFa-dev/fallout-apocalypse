import { MainScene } from '@features/game/components/phaser/scene/main/main.scene';
import { GridService } from '@features/game/services/domain/grid.service';
import { MovementService } from '@features/game/services/domain/movement.service';
import { PlayerService } from '@features/game/services/domain/player.service';
import { TerrainService } from '@features/game/services/domain/terrain.service';
import { MapStore } from '@features/game/stores/map.store';
import Phaser, { Game } from 'phaser';
import BoardPlugin from 'phaser3-rex-plugins/plugins/board-plugin';

export const CANVAS_ID = 'game-canvas';

export const StartGame = (
  parent: string,
  gridService: GridService,
  movementService: MovementService,
  playerService: PlayerService,
  terrainService: TerrainService,
  mapStore: MapStore
) => {

  // Il n'existe pas d'injection automatique pour les scènes Phaser dans Angular, et il faut donc passer manuellement les dépendances.
  // https://phaser.discourse.group/t/how-dose-dependency-injection-in-phaser-scenes/13058/2
  const mainScene = new MainScene(
    gridService,
    movementService,
    playerService,
    terrainService,
    mapStore
  );

  const gameConfig: Phaser.Types.Core.GameConfig = {
    type: Phaser.AUTO,
    scale: {
      mode: Phaser.Scale.FIT,
      autoCenter: Phaser.Scale.CENTER_BOTH,
      parent: parent,
      width: 800,
      height: 600
    },
    backgroundColor: '#2d2d2d',
    physics: {
      default: 'arcade',
      arcade: {
        gravity: { x: 0, y: 0 },
        debug: true
      }
    },
    scene: [mainScene],
    plugins: {
      scene: [{
        key: 'rexBoard',
        plugin: BoardPlugin,
        mapping: 'rexBoard'
      }]
    }
  };

  return new Game(gameConfig);
}

