package duongnguyen.chess.domain.driver;

import duongnguyen.chess.domain.model.GameEvent;

public interface GameEventListener {
    void handleEvent(GameEvent event);
}
