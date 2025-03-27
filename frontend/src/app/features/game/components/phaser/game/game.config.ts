import { MainScene } from '@features/game/components/phaser/scene/main/main.scene';
import { InitializerService } from '@features/game/services/domain/initializer.service';
import { MapStore } from '@features/game/stores/map.store';
import Phaser, { Game } from 'phaser';
import BoardPlugin from 'phaser3-rex-plugins/plugins/board-plugin';

export const CANVAS_ID = 'game-canvas';

export const StartGame = (
  parent: string,
  initializerService: InitializerService,
  mapStore: MapStore
) => {

  // Il n'existe pas d'injection automatique pour les scènes Phaser dans Angular, et il faut donc passer manuellement les dépendances.
  // https://phaser.discourse.group/t/how-dose-dependency-injection-in-phaser-scenes/13058/2
  const mainScene = new MainScene(
    initializerService,
    mapStore
  );

  const gameConfig: Phaser.Types.Core.GameConfig = {
    type: Phaser.AUTO,
    scale: {
      mode: Phaser.Scale.EXPAND,
      autoCenter: Phaser.Scale.EXPAND,
      parent: parent
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

