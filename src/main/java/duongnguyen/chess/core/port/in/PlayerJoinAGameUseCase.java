package duongnguyen.chess.core.port.in;

import duongnguyen.chess.core.driver.GamePlayer;

public interface PlayerJoinAGameUseCase {
    GamePlayer playerJoinAGame(String gameId, String playerId);
}
