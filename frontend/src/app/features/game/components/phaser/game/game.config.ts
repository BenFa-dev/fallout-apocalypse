import { EnvironmentInjector, runInInjectionContext } from '@angular/core';
import { createMainScene } from '@features/game/components/phaser/scene/main/main.scene';
import Phaser from 'phaser';
import BoardPlugin from 'phaser3-rex-plugins/plugins/board-plugin';

export const CANVAS_ID = 'game-canvas';

export const StartGame = (parent: string, injector: EnvironmentInjector) =>
  new Phaser.Game({
    type: Phaser.AUTO,
    scale: {
      mode: Phaser.Scale.EXPAND,
      autoCenter: Phaser.Scale.EXPAND,
      parent
    },
    backgroundColor: '#2d2d2d',
    physics: {
      default: 'arcade',
      arcade: { gravity: { x: 0, y: 0 }, debug: true }
    },
    scene: [
      // Ajout du contexte d'injection dans MainScene, pour éviter de passer via le constructeur les injections
      runInInjectionContext(injector, () => createMainScene(injector))
    ],
    plugins: {
      scene: [
        {
          key: 'rexBoard',
          plugin: BoardPlugin,
          mapping: 'rexBoard'
        }
      ]
    }
  });
