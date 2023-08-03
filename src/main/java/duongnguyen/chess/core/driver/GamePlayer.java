package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.model.Color;
import duongnguyen.chess.core.model.GameCommand;

public class GamePlayer {
    private final GameCommandHandler commandHandler;
    private final Color color;


    public GamePlayer(GameCommandHandler commandHandler, Color color) {
        this.commandHandler = commandHandler;
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void sendGameCommand(GameCommand command) {
        commandHandler.handleCommand(command);
    }
}
