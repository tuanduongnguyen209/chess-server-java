package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.model.BoardState;
import duongnguyen.chess.core.board.GameStatus;

public record GameEvent (GameEventType type, GameStatus status, BoardState boardState) {
}
