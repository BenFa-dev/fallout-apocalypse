declare module 'phaser3-rex-plugins/plugins/board-plugin' {

  export interface BoardConfig {
    grid: {
      gridType: 'quadGrid' | 'hexagonGrid';
      x: number;
      y: number;
      cellWidth: number;
      cellHeight: number;
      type: 'orthogonal' | 'isometric';
    };
  }

  export interface Board {
    grid: {
      getWorldXY: (x: number, y: number) => { x: number; y: number } | null;
    };

    worldXYToTileXY(worldX: number, worldY: number): Position | null;

    tileXYToWorldXY(tileX: number, tileY: number): { x: number, y: number };
  }

  export default class RexBoardPlugin extends Phaser.Plugins.ScenePlugin {
    add: {
      board: (config: BoardConfig) => Board;
    };

  }
}

declare module 'phaser3-rex-plugins/plugins/board' {
  export type { Board, BoardConfig } from 'phaser3-rex-plugins/plugins/board-plugin';
}
