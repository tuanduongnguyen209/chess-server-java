package duongnguyen.chess.domain.port.in;

import duongnguyen.chess.domain.driver.GameEventListener;
import duongnguyen.chess.domain.driver.GameMaster;
import duongnguyen.chess.domain.model.GameCommand;
import duongnguyen.chess.domain.model.GamePlayer;

public interface GameDispatcherUseCase {
    void createANewGame(String gameId);
    void playerJoinAGame(String gameId, String playerId);
    void playerSendCommand(GameCommand command);
    GamePlayer getPlayer(String playerId);
    GameMaster getGame(String gameId);
    void registerGameEventListener(String gameId, GameEventListener listener);
    void unregisterGameEventListener(String gameId, GameEventListener listener);
}
