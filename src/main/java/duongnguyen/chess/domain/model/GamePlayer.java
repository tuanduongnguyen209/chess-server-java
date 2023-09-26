package duongnguyen.chess.domain.model;

public record GamePlayer(
        String id,
        Color color) {

    public Color getColor() {
        return color;
    }
}
