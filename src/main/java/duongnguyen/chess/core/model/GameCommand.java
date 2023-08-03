package duongnguyen.chess.core.model;

public record GameCommand(GameCommandType type, Move move, Color color) {
    public static record Move(int fromX, int fromY, int toX, int toY) {}
}
