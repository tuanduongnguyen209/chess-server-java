package duongnguyen.chess.domain.model;

public record ImmutableMove(int fromX, int fromY, int toX, int toY, String piece, Color color) {
}
