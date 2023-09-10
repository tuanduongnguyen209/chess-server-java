package duongnguyen.chess.core.port.in;

import duongnguyen.chess.core.driver.GameEventListener;
import duongnguyen.chess.core.driver.GameMaster;
import duongnguyen.chess.core.driver.GamePlayer;

public interface GameManagerPort {
    void createANewGame(String gameId);
    void playerJoinAGame(String gameId, String playerId);
    GamePlayer getPlayer(String playerId);
    GameMaster getGame(String gameId);
    void registerGameEventListener(String gameId, GameEventListener listener);
    void unregisterGameEventListener(String gameId, GameEventListener listener);
}
