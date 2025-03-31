import { inject, Injectable } from '@angular/core';
import { GridService } from '@features/game/services/domain/grid.service';
import { MovementService } from '@features/game/services/domain/movement.service';
import { PhaserService } from '@features/game/services/domain/phaser.service';
import { PlayerService } from '@features/game/services/domain/player.service';
import { TooltipService } from '@features/game/services/domain/tooltip.service';
import { MapStore } from '@features/game/stores/map.store';
import { Scene } from 'phaser';
import { Board } from 'phaser3-rex-plugins/plugins/board-plugin';

@Injectable({
  providedIn: 'root'
})
export class InitializerService {
  private readonly gridService = inject(GridService);
  private readonly mapStore = inject(MapStore);
  private readonly movementService = inject(MovementService);
  private readonly phaserService = inject(PhaserService);
  private readonly playerService = inject(PlayerService);
  private readonly tooltipService = inject(TooltipService);

  initialize(scene: Scene, board: Board) {
    this.phaserService.initialize(scene, board);
    this.tooltipService.createTooltipModel();
    this.gridService.createGridLines(this.mapStore.tiles());
    this.playerService.createPlayer();
    this.movementService.setupKeyboardControls();
  }
}
