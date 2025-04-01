import { inject, Injectable } from '@angular/core';
import { PhaserStore } from '@features/game/stores/phaser.store';
import { Scene } from 'phaser';

@Injectable({ providedIn: 'root' })
export class TooltipService {
  private readonly phaserStore = inject(PhaserStore);

  private scene?: Scene;
  private background!: Phaser.GameObjects.Graphics;
  private text!: Phaser.GameObjects.Text;
  private padding = 6;

  createTooltipModel() {
    this.scene = this.phaserStore.existingScene();

    if (this.scene) {
      this.background = this.scene.add.graphics()
        .setDepth(9999)
        .setScrollFactor(0)
        .setVisible(false);

      this.text = this.scene.add.text(0, 0, '', {
        fontFamily: 'monospace',
        fontSize: '14px',
        color: '#00FF00',
        backgroundColor: 'rgba(0, 0, 0, 0.8)',
        padding: { x: this.padding, y: this.padding },
        stroke: '#FFD700',
        strokeThickness: 1,
        shadow: {
          offsetX: 1,
          offsetY: 1,
          color: '#003300',
          blur: 2,
          fill: true
        }
      })
        .setDepth(10000)
        .setScrollFactor(0)
        .setVisible(false);
    }
  }

  createTooltip(sprite: Phaser.GameObjects.Sprite, text: string) {
    if (!this.scene) return;

    sprite.setInteractive();

    // 1) pointerover → on affiche
    sprite.on('pointerover', (pointer: PointerEvent) => {
      this.show(text, pointer.x, pointer.y);
    });

    // 2) pointermove → on met juste à jour la position
    sprite.on('pointermove', (pointer: PointerEvent) => {
      this.updatePosition(pointer.x, pointer.y);
    });

    // 3) gameout → on cache
    this.scene.input.on('gameout', () => {
      this.hide()
    });
  }

  private show(text: string, x: number, y: number) {
    if (!this.scene) return;

    this.text.setText(text).setVisible(true).setAlpha(0);
    this.background.setVisible(true).setAlpha(0);

    this.scene.tweens.add({
      targets: [this.text, this.background],
      alpha: 1,
      duration: 150
    });

    // Position initiale
    this.updatePosition(x, y);
  }

  private hide() {
    if (!this.scene) return;

    this.scene.tweens.add({
      targets: [this.text, this.background],
      alpha: 0,
      duration: 150,
      onComplete: () => {
        this.text.setVisible(false);
        this.background.setVisible(false);
      }
    });
  }

  private updatePosition(x: number, y: number) {
    if (!this.scene) return;

    const width = this.text.width + this.padding * 2;
    const height = this.text.height + this.padding * 2;

    // Empêche de déborder de l'écran
    x = Math.min(x, this.scene.scale.width - width - 10);
    y = Math.min(y, this.scene.scale.height - height - 10);

    // Positionne le texte et la bordure
    this.text.setPosition(x + 10, y + 10);

    this.background.clear()
      .fillStyle(0x000000, 0.8)
      .fillRect(x + 7, y + 7, width, height)
      .lineStyle(2, 0xFFD700, 1)
      .strokeRect(x + 7, y + 7, width, height);
  }
}
