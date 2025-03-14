export enum GameEventType {
  CHARACTER_ENTER = "CHARACTER_ENTER"
}

export interface GameEvent {
  type: GameEventType;
}

export interface CharacterMovementEvent {
  userId: string;
  characterId: number;
  newX: number;
  newY: number;
  remainingActionPoints: number;
  timestamp: number;
  eventTypes: GameEventType[];
}
