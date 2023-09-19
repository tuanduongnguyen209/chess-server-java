package duongnguyen.chess.domain.model;

import duongnguyen.chess.domain.model.Color;

public record GamePlayer(
        Color color) {

    public Color getColor() {
        return color;
    }
}
