import { TerrainConfiguration } from '@features/game/models/terrain-configuration.model';
import { GridService } from '@features/game/services/domain/grid.service';
import { MovementService } from '@features/game/services/domain/movement.service';
import { PlayerService } from '@features/game/services/domain/player.service';
import { TerrainService } from '@features/game/services/domain/terrain.service';
import { MapStore } from '@features/game/stores/map.store';
import { Scene } from 'phaser';
import type { Board, default as RexBoardPlugin } from 'phaser3-rex-plugins/plugins/board-plugin';

export class MainScene extends Scene {
  private board!: Board;
  public readonly rexBoard!: RexBoardPlugin;

  constructor(
    private gridService: GridService,
    private movementService: MovementService,
    private playerService: PlayerService,
    private terrainService: TerrainService,
    private readonly mapStore: MapStore
  ) {
    super({ key: 'MainScene' });
  }

  /** Précharge les assets nécessaires au jeu */
  preload() {
    this.loadTerrainAssets()
    console.log('✅ Assets de terrains chargés avec succès');

    this.load.svg('player', '/assets/textures/player.svg');
    console.log('✅ Asset du joueur chargé avec succès');

  }

  create() {
    this.setupBoard();
    this.initializeServices();
  }

  /** Charge les assets des terrains */
  private loadTerrainAssets() {
    const terrainConfigs = this.mapStore.terrainConfigurations();
    terrainConfigs.forEach((config: TerrainConfiguration) => {
      const type = config.name.toLowerCase().replace(/_/g, '-');
      this.load.svg(type, config.svgPath);
    });
  }

  /** Configure le plateau de jeu avec le plugin RexBoard */
  private setupBoard() {
    if (!this.rexBoard) {
      throw new Error('RexBoard plugin not initialized');
    }

    this.board = this.rexBoard.add.board({
      grid: {
        gridType: 'quadGrid',
        x: 400,
        y: 300,
        cellWidth: 128,
        cellHeight: 128,
        type: 'orthogonal'
      }
    });

    // Ajuster la caméra
    this.cameras.main.setZoom(0.8);
    this.cameras.main.centerOn(400, 300);
  }

  /** Initialise les services avec la scène */
  private initializeServices() {
    console.log('🎮 Initialisation des services');
    this.gridService.initialize(this, this.board);
    this.terrainService.initialize(this);
    this.playerService.initialize(this);
    this.movementService.initialize(this);
  }

  override update() {
  }
}
