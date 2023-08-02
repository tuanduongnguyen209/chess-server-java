package duongnguyen.chess.core.port.in;

import duongnguyen.chess.core.driver.GamePlayer;

public interface PlayerJoinAGamePort {
    GamePlayer playerJoinAGame(String gameId, String playerId);
}
