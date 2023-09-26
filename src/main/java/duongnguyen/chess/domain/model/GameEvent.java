package duongnguyen.chess.domain.model;

public record GameEvent(GameEventType type, GameStatus status, BoardState boardState, String gameId, String playerId,
                        Color color) {
    public GameEvent(GameEventType type, GameStatus status, BoardState boardState, String gameId) {
        this(type, status, boardState, gameId, null, null);
    }
    public GameEvent(GameEventType boardStateChanged, GameStatus status, BoardState boardState) {
        this(boardStateChanged, status, boardState, null, null, null);
    }
}
