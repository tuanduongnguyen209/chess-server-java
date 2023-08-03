package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.model.GameCommand;

public interface GameCommandHandler {
    void handleCommand(GameCommand command);
}
