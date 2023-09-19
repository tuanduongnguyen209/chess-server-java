package duongnguyen.chess.domain.model;

public record GameCommand(GameCommandType type, Move move, String playerId, String gameId) {
    public static record Move(int fromX, int fromY, int toX, int toY) {}
}
