import type { Board as RexBoard } from 'phaser3-rex-plugins/plugins/board';

/** Interface définissant la structure du plateau de jeu */
export interface Board {
    grid: RexBoard['grid'];
} 