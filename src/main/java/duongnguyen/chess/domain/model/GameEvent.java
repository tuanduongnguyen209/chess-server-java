package duongnguyen.chess.domain.model;

public record GameEvent(GameEventType type, GameStatus status, BoardState boardState, String gameId, String playerId,
                        Color color) {
    public GameEvent(GameEventType boardStateChanged, GameStatus status, BoardState boardState) {
        this(boardStateChanged, status, boardState, null, null, null);
    }
}
