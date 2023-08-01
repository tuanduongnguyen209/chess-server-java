package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.model.Color;

public record GameCommand(GameCommandType type, Move move, Color color) {
    public static record Move(int fromX, int fromY, int toX, int toY) {}
}
