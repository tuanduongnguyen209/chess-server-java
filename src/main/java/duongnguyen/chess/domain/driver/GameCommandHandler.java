package duongnguyen.chess.domain.driver;

import duongnguyen.chess.domain.model.Color;
import duongnguyen.chess.domain.model.GameCommand;

public interface GameCommandHandler {
    void handleCommand(GameCommand command, Color playerColor);
}
