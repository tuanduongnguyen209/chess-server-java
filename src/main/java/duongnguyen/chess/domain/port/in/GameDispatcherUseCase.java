package duongnguyen.chess.domain.port.in;

import duongnguyen.chess.domain.driver.GameEventListener;
import duongnguyen.chess.domain.model.GameCommand;

public interface GameDispatcherUseCase {
    void playerJoinAGame(String gameId, String playerId);
    void playerSendCommand(GameCommand command);
    void registerGameEventListener(String gameId, GameEventListener listener);
    void unregisterGameEventListener(String gameId, GameEventListener listener);
}
