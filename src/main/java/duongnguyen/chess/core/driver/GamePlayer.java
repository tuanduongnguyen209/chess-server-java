package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.model.GameCommand;

public record GamePlayer(GameCommandHandler commandHandler,
                         Color color) {

    public Color getColor() {
        return color;
    }

    public void sendGameCommand(GameCommand command) {
        commandHandler.handleCommand(command);
    }
}
