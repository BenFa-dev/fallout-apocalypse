import { environment } from '@environments/environment.development';
import { TerrainConfiguration } from '@features/game/models/terrain-configuration.model';
import { InitializerService } from '@features/game/services/domain/initializer.service';
import { MapStore } from '@features/game/stores/map.store';
import { Scene } from 'phaser';
import type { Board, default as RexBoardPlugin } from 'phaser3-rex-plugins/plugins/board-plugin';

export class MainScene extends Scene {
  private board!: Board;
  public readonly rexBoard!: RexBoardPlugin;

  constructor(
    private initializerService: InitializerService,
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
      this.load.image(type, config.path);
    });
    this.load.image("unknown", "/assets/textures/terrains/unknown.png");
  }

  /** Configure le plateau de jeu avec le plugin RexBoard */
  private setupBoard() {
    if (!this.rexBoard) {
      throw new Error('RexBoard plugin not initialized');
    }

    this.board = this.rexBoard.add.board({
      grid: {
        gridType: 'quadGrid',
        x: 0,
        y: 0,
        cellWidth: environment.scene.board.size,
        cellHeight: environment.scene.board.size,
        type: 'orthogonal'
      }
    });
  }

  /** Initialise les services avec la scène */
  private initializeServices() {
    console.log('🎮 Initialisation des services');
    this.initializerService.initialize(this, this.board);
  }

  override update() {
  }
}
