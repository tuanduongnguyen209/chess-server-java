package duongnguyen.chess.core.driver;

import duongnguyen.chess.core.model.GameEvent;

public interface GameEventListener {
    void handleEvent(GameEvent event);
}
