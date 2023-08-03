package duongnguyen.chess.core.model;

public record GameEvent (GameEventType type, GameStatus status, BoardState boardState) {
}
