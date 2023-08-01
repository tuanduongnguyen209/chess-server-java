package duongnguyen.chess.core.model;

public enum GameStatus {
    IN_PROGRESS,
    CHECK,
    CHECKMATE,
    STALEMATE,
    DRAW_INSUFFICIENT_MATERIAL,
    DRAW_THREEFOLD_REPETITION,
    DRAW_FIFTY_MOVE_RULE,
    DRAW_AGREEMENT
}
